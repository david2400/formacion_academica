package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers.AcudienteMapper;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.AcudienteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.AcudienteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AcudienteJpaAdapter implements AcudienteRepositoryPort {

    private final AcudienteJpaRepository acudienteJpaRepository;
    private final AcudienteMapper acudienteMapper;

    @Override
    public Acudiente guardar(CrearAcudienteDto request) {
        AcudienteEntity entity = acudienteMapper.toEntity(request);
        return acudienteMapper.toDomainModel(acudienteJpaRepository.save(entity));
    }

    @Override
    public Acudiente actualizar(ActualizarAcudienteDto request) {
        AcudienteEntity entity = acudienteJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
        acudienteMapper.updateEntityFromDto(request, entity);
        return acudienteMapper.toDomainModel(acudienteJpaRepository.save(entity));
    }

    @Override
    public Optional<Acudiente> obtenerPorId(Long acudienteId) {
        return acudienteJpaRepository.findById(acudienteId).map(acudienteMapper::toDomainModel);
    }

    @Override
    public Optional<Acudiente> obtenerPorTipoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        // numero_documento no tiene restriccion unica en acudientes (a diferencia
        // de estudiantes), asi que puede haber mas de una coincidencia para el
        // mismo tipo+numero. Se toma la mas reciente (mayor id) en vez de dejar
        // que la consulta reviente con NonUniqueResultException.
        return acudienteJpaRepository.findByTipoDocumentoAndNumeroDocumentoOrderByIdDesc(tipoDocumento, numeroDocumento)
                .stream()
                .findFirst()
                .map(acudienteMapper::toDomainModel);
    }

    @Override
    public boolean existePorTipoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        return acudienteJpaRepository.existsByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
    }

    @Override
    public boolean existePorTipoYNumeroDocumentoConIdDiferente(String tipoDocumento, String numeroDocumento, Long id) {
        return acudienteJpaRepository.existsByTipoDocumentoAndNumeroDocumentoAndIdNot(tipoDocumento, numeroDocumento, id);
    }

    @Override
    public List<Acudiente> listarPorEstudiante(Long estudianteId) {
        return acudienteMapper.toDomainModelList(acudienteJpaRepository.findByEstudianteId(estudianteId));
    }

    @Override
    public boolean existePrincipalParaEstudiante(Long estudianteId, Long excluirAcudienteId) {
        return acudienteJpaRepository.existsByEstudianteIdAndEsPrincipalIsTrue(estudianteId, excluirAcudienteId);
    }

    @Override
    public void eliminar(Long acudienteId) {
        acudienteJpaRepository.deleteById(acudienteId);
    }
}
