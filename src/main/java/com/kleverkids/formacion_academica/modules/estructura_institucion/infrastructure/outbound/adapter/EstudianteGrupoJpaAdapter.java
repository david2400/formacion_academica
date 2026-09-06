package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estados.application.input.contexto.ConsultarEstadoContextoUseCase;
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
     * Contexto con el que este recurso está registrado en el catálogo central, que
     * vive en el servicio access_control.
     *
     * <p>Es la terna <b>aplicación · módulo · entidad</b>: identifica de forma única
     * a quién pertenecen estos estados. Debe coincidir con el {@code codigo} del
     * contexto registrado allí.
     */
    public static final String CONTEXTO = "formacion_academica.estructura_institucion.estudiante_grupo";

    private final EstudianteGrupoJpaRepository estudianteGrupoJpaRepository;
    private final EstudianteGrupoMapper estudianteGrupoMapper;
    private final ConsultarEstadoContextoUseCase estadosDelContexto;

    /**
     * Asigna un estudiante a un grupo.
     *
     * <p>El estado no viene del cliente: se toma el que esté marcado como inicial para
     * el contexto {@code estudiante_grupo}.
     *
     * <p>Es idempotente respecto a la clave {@code (estudiante_id, grupo_id)}: si el
     * estudiante ya estuvo en el grupo y fue removido, la fila se revive en vez de
     * insertar un duplicado —que además la restricción única rechazaría—. Si la
     * asignación ya está activa se devuelve tal cual.
     */
    @Override
    public EstudianteGrupo asignar(AsignarEstudianteGrupoDto request) {
        Long estadoInicial = estadosDelContexto.requerirEstadoInicial(CONTEXTO, request.getIdEmpresa());
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
            return estudianteGrupoJpaRepository.findById(id)
                    .map(estudianteGrupoMapper::toDomainModel)
                    .orElseThrow(() -> new IllegalStateException("No se pudo reactivar la asignación " + id));
        }

        EstudianteGrupoEntity entity = estudianteGrupoMapper.toEntity(request);
        entity.setFechaAsignacion(fecha);
        entity.setEstadoId(estadoInicial);
        return estudianteGrupoMapper.toDomainModel(estudianteGrupoJpaRepository.save(entity));
    }

    /**
     * Cambia el estado validando contra la parametrización del contexto, de modo que
     * no se pueda colar un estado que no aplica a este recurso.
     */
    @Override
    public EstudianteGrupo cambiarEstado(CambiarEstadoEstudianteGrupoDto request) {
        if (!estadosDelContexto.estaRegistrado(CONTEXTO, request.getNuevoEstadoId(), request.getIdEmpresa())) {
            throw new IllegalArgumentException("El estado " + request.getNuevoEstadoId()
                    + " no está habilitado para el contexto '" + CONTEXTO + "'");
        }

        EstudianteGrupoEntity entity = estudianteGrupoJpaRepository.findById(request.getAsignacionId())
                .orElseThrow(() -> new NotFoundException("Asignación no encontrada"));

        entity.setEstadoId(request.getNuevoEstadoId());
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
