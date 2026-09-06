package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo.GrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.GrupoMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.GrupoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrupoJpaAdapter implements GrupoRepositoryPort {

    /** Máquina que gobierna el ciclo de vida del grupo en el motor de estados. */
    public static final String MAQUINA = "GRUPO_LIFECYCLE";

    /** Tipo de entidad con el que el motor identifica este recurso. */
    public static final String TIPO_ENTIDAD = "GRUPO";

    private final GrupoJpaRepository grupoJpaRepository;
    private final GrupoMapper grupoMapper;
    private final MotorEstadosPort motorEstados;

    public GrupoJpaAdapter(GrupoJpaRepository grupoJpaRepository,
                           GrupoMapper grupoMapper,
                           MotorEstadosPort motorEstados) {
        this.grupoJpaRepository = grupoJpaRepository;
        this.grupoMapper = grupoMapper;
        this.motorEstados = motorEstados;
    }

    /**
     * El estado inicial lo decide el motor, no un id fijo en el mapper.
     *
     * <p>El ciclo se arranca después de guardar porque el motor necesita el id
     * definitivo del grupo para crear la instancia.
     */
    @Override
    public Grupo guardar(CrearGrupoDto request) {
        GrupoEntity entity = grupoMapper.toEntity(request);
        entity.setEstadoId(motorEstados.estadoInicial(MAQUINA).intValue());

        GrupoEntity guardado = grupoJpaRepository.save(entity);
        motorEstados.iniciarCiclo(MAQUINA, TIPO_ENTIDAD, guardado.getId());

        return grupoMapper.toDomainModel(guardado);
    }

    @Override
    public Grupo actualizar(ActualizarGrupoDto request) {
        GrupoEntity entity = grupoJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));
        grupoMapper.updateEntityFromDto(request, entity);
        return grupoMapper.toDomainModel(grupoJpaRepository.save(entity));
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return grupoJpaRepository.existsByCodigo(codigo);
    }

    @Override
    public Grupo obtenerPorId(Long id) {
        return grupoJpaRepository.findById(id)
                .map(grupoMapper::toDomainModel)
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));
    }

    @Override
    public List<Grupo> listar() {
        return grupoMapper.toDomainModelList(grupoJpaRepository.findAll());
    }

    @Override
    public void eliminar(Long id) {
        grupoJpaRepository.deleteById(id);
    }
}
