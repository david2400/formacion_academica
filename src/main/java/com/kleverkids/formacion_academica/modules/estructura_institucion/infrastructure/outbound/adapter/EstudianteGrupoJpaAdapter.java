package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoMotor;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.EstudianteGrupoMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.EstudianteGrupoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.EstudianteGrupoJpaRepository;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.GrupoJpaRepository;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class EstudianteGrupoJpaAdapter implements EstudianteGrupoRepositoryPort {

    /**
     * Máquina que gobierna el ciclo de vida de la asignación, definida en el motor
     * de estados de access_control.
     *
     * <p>Debe coincidir con el {@code code} de una máquina PUBLISHED allí. Si no
     * existe, la asignación falla al arrancar en vez de inventarse un estado.
     */
    public static final String MAQUINA = "ASIGNACION_GRUPO_LIFECYCLE";

    /** Tipo de entidad con el que el motor identifica este recurso. */
    public static final String TIPO_ENTIDAD = "ESTUDIANTE_GRUPO";

    private final EstudianteGrupoJpaRepository estudianteGrupoJpaRepository;
    private final EstudianteGrupoMapper estudianteGrupoMapper;
    private final MotorEstadosPort motorEstados;
    private final GrupoJpaRepository grupoJpaRepository;

    /**
     * Asigna un estudiante a un grupo.
     *
     * <p>El estado no viene del cliente: es el inicial de la máquina, que decide el
     * motor.
     *
     * <p>No es idempotente frente a una fila ya existente y no eliminada para la
     * misma clave {@code (estudiante_id, grupo_id)} —da igual en qué estado esté—:
     * eso significa que el estudiante ya pasó por este grupo (o sigue en él) y se
     * rechaza con un conflicto explícito en vez de devolver la fila existente en
     * silencio, que es lo que ocultaba el problema al usuario tanto en la consulta
     * previa del frontend como en el propio insert. El único caso que sí se resuelve
     * de forma transparente es el de una fila <b>eliminada lógicamente</b> (removida
     * con DELETE): esa sí se revive en vez de insertar un duplicado, que además la
     * restricción única rechazaría.
     *
     * <p>Al revivir una fila también se rearranca el ciclo, porque la instancia
     * anterior quedó en un estado final. {@code iniciarCiclo} es idempotente, así que
     * la llamada es segura aunque el motor ya tuviera la instancia.
     */
    @Override
    public EstudianteGrupo asignar(AsignarEstudianteGrupoDto request) {
        Long estadoInicial = motorEstados.estadoInicial(MAQUINA);
        LocalDate fecha = request.getFechaAsignacion() != null ? request.getFechaAsignacion() : LocalDate.now();

        Optional<EstudianteGrupoEntity> activa = estudianteGrupoJpaRepository
                .findByEstudianteIdAndGrupoId(request.getEstudianteId(), request.getGrupoId());
        if (activa.isPresent()) {
            throw new IllegalStateException("El estudiante ya está asignado a este grupo.");
        }

        // Regla de negocio: un estudiante no puede tener más de un grupo activo
        // a la vez. En este punto ya se descartó el caso "ya está en ESTE
        // grupo" (arriba, ahora rechazado explícitamente), así que cualquier
        // asignación no final que quede es necesariamente en OTRO grupo.
        validarSinOtraAsignacionActiva(request.getEstudianteId(), request.getGrupoId());

        // Solo la consulta nativa ve las filas borradas lógicamente.
        Optional<EstudianteGrupoEntity> removida = estudianteGrupoJpaRepository
                .buscarIncluyendoEliminados(request.getEstudianteId(), request.getGrupoId());
        if (removida.isPresent()) {
            Long id = removida.get().getId();
            estudianteGrupoJpaRepository.reactivar(id, estadoInicial, fecha);
            motorEstados.iniciarCiclo(MAQUINA, TIPO_ENTIDAD, id);
            return estudianteGrupoJpaRepository.findById(id)
                    .map(estudianteGrupoMapper::toDomainModel)
                    .orElseThrow(() -> new IllegalStateException("No se pudo reactivar la asignación " + id));
        }

        EstudianteGrupoEntity entity = estudianteGrupoMapper.toEntity(request);
        entity.setFechaAsignacion(fecha);
        entity.setEstadoId(estadoInicial);

        EstudianteGrupoEntity guardada = estudianteGrupoJpaRepository.save(entity);
        motorEstados.iniciarCiclo(MAQUINA, TIPO_ENTIDAD, guardada.getId());

        return estudianteGrupoMapper.toDomainModel(guardada);
    }

    /**
     * Cambia el estado a través del motor.
     *
     * <p>Ya no basta con que el estado exista: el motor comprueba además que la
     * transición sea válida <b>desde el estado actual</b>, que se cumplan sus reglas
     * y que haya motivo cuando la transición lo exige. Eso es lo que antes no
     * validaba nadie.
     *
     * <p>El {@code estadoId} local se sobrescribe con lo que devuelve el motor, no
     * con lo que pidió el cliente: si ambos discrepan, la fuente de verdad es el
     * motor.
     */
    @Override
    public EstudianteGrupo cambiarEstado(CambiarEstadoEstudianteGrupoDto request) {
        EstudianteGrupoEntity entity = estudianteGrupoJpaRepository.findById(request.getAsignacionId())
                .orElseThrow(() -> new NotFoundException("Asignación no encontrada"));

        Long estadoResultante = motorEstados.moverAEstado(
                MAQUINA, TIPO_ENTIDAD, entity.getId(),
                request.getNuevoEstadoId(), request.getMotivo());

        entity.setEstadoId(estadoResultante);
        return estudianteGrupoMapper.toDomainModel(estudianteGrupoJpaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteGrupo> listarPorGrupo(Long grupoId) {
        return estudianteGrupoMapper.toDomainModelList(estudianteGrupoJpaRepository.findByGrupoIdOrderByIdAsc(grupoId));
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteGrupo consultarPorId(Long estudianteGrupoId) {
        return estudianteGrupoJpaRepository.findById(estudianteGrupoId)
                .map(estudianteGrupoMapper::toDomainModel)
                .orElseThrow(() -> new NotFoundException("Asignación de estudiante grupo no encontrada"));
    }

    /** Borrado lógico: la fila se conserva y puede revivirse al reasignar. */
    @Override
    public void eliminar(Long estudianteGrupoId) {
        if (!estudianteGrupoJpaRepository.existsById(estudianteGrupoId)) {
            throw new NotFoundException("Asignación de estudiante grupo no encontrada");
        }
        estudianteGrupoJpaRepository.deleteById(estudianteGrupoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteGrupo> listar() {
        return estudianteGrupoMapper.toDomainModelList(estudianteGrupoJpaRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteGrupo> listarPorEstudiante(Long estudianteId) {
        return estudianteGrupoMapper.toDomainModelList(
                estudianteGrupoJpaRepository.findByEstudianteIdOrderByIdAsc(estudianteId));
    }

    /**
     * Impide que un estudiante quede con dos asignaciones activas a la vez.
     *
     * <p>"Activa" no es un valor fijo aquí: es cualquier estado de
     * {@code ASIGNACION_GRUPO_LIFECYCLE} que el motor marque como no final
     * ({@code es_final = false}). Hoy solo existe uno (ACTIVO), pero esto no lo
     * asume —consulta el catálogo real— para no romperse si mañana el motor
     * agrega, por ejemplo, un estado intermedio no final.
     *
     * <p>Solo mira asignaciones en <b>otros</b> grupos: una fila para este
     * mismo grupo ya se rechazó antes de llegar aquí (o, si estaba eliminada
     * lógicamente, se reactiva sin pasar por esta validación), así que nunca
     * cuenta como conflicto consigo misma.
     */
    private void validarSinOtraAsignacionActiva(Long estudianteId, Long grupoId) {
        Set<Long> estadosActivos = estadosNoFinales();

        estudianteGrupoJpaRepository.findByEstudianteIdOrderByIdAsc(estudianteId).stream()
                .filter(asignacion -> !asignacion.getGrupoId().equals(grupoId))
                .filter(asignacion -> estadosActivos.contains(asignacion.getEstadoId()))
                .findFirst()
                .ifPresent(asignacion -> {
                    String nombreGrupo = grupoJpaRepository.findById(asignacion.getGrupoId())
                            .map(GrupoEntity::getNombre)
                            .orElse("otro grupo");
                    throw new IllegalStateException(
                            "El estudiante ya tiene una asignación activa en el grupo \"" + nombreGrupo
                                    + "\". Debe finalizar, reprobar o retirar esa asignación antes de crear una nueva.");
                });
    }

    /** Ids de los estados de {@link #MAQUINA} que el motor no marca como finales. */
    private Set<Long> estadosNoFinales() {
        return motorEstados.estadosDe(MAQUINA).stream()
                .filter(estado -> !estado.esFinal())
                .map(EstadoMotor::id)
                .collect(Collectors.toSet());
    }
}
