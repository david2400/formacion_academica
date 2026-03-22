package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaBancoRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CriterioBusquedaPregunta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Pregunta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.PreguntaBanco;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.PreguntaBancoMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaBancoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.PreguntaJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.PreguntaBancoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PreguntaJpaAdapter implements PreguntaRepository, PreguntaBancoRepositoryPort {

    // Repositorios del sistema en inglés
    private final PreguntaJpaRepository preguntaJpaRepository;
    
    // Repositorios del sistema en español
    private final PreguntaBancoJpaRepository preguntaBancoJpaRepository;
    private final PreguntaBancoMapper preguntaBancoMapper;

    // Implementación de PreguntaRepository (español)
    @Override
    public Pregunta save(Pregunta question) {
        // Implementación mínima por ahora - retornar null para evitar errores
        return null;
    }
    
    @Override
    public Optional<Pregunta> findById(Long id) {
        // Implementación mínima por ahora - retornar empty para evitar errores
        return Optional.empty();
    }
    
    @Override
    public void deleteById(Long id) {
        preguntaJpaRepository.deleteById(id);
    }
    
    @Override
    public Page<Pregunta> search(CriterioBusquedaPregunta criteria, Pageable pageable) {
        // Implementación mínima por ahora - retornar página vacía para evitar errores
        return Page.empty();
    }
    
    @Override
    public boolean existsById(Long id) {
        return preguntaJpaRepository.existsById(id);
    }

    // Implementación de PreguntaBancoRepositoryPort (español)
    @Override
    public PreguntaBanco guardar(CrearPreguntaBancoDto request) {
        PreguntaBancoEntity entity = preguntaBancoMapper.toEntity(request);
        return preguntaBancoMapper.toDomainModel(preguntaBancoJpaRepository.save(entity));
    }

    @Override
    public PreguntaBanco actualizar(ActualizarPreguntaBancoDto request) {
        PreguntaBancoEntity entity = preguntaBancoJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada"));
        preguntaBancoMapper.applyUpdate(entity, request);
        return preguntaBancoMapper.toDomainModel(preguntaBancoJpaRepository.save(entity));
    }

    @Override
    public List<PreguntaBanco> listarPorTematica(Long tematicaId) {
        return preguntaBancoMapper.toDomainModelList(preguntaBancoJpaRepository.findByTematicaId(tematicaId));
    }

    @Override
    public PreguntaBanco obtenerPorId(Long preguntaId) {
        return preguntaBancoJpaRepository.findById(preguntaId)
                .map(preguntaBancoMapper::toDomainModel)
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada"));
    }

    @Override
    public void eliminar(Long preguntaId) {
        preguntaBancoJpaRepository.deleteById(preguntaId);
    }
}
