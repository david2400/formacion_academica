package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.EstudianteGrupoMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.EstudianteGrupoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.EstudianteGrupoJpaRepository;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    /**
     * Asigna un estudiante a un grupo.
     *
     * <p>El estado no viene del cliente: es el inicial de la máquina, que decide el
     * motor.
     *
     * <p>Es idempotente respecto a la clave {@code (estudiante_id, grupo_id)}: si el
     * estudiante ya estuvo en el grupo y fue removido, la fila se revive en vez de
     * insertar un duplicado —que además la restricción única rechazaría—. Si la
     * asignación ya está activa se devuelve tal cual.
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
            return estudianteGrupoMapper.toDomainModel(activa.get());
        }

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
}
