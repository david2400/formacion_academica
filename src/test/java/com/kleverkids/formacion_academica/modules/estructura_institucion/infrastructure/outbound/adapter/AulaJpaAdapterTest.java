package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.AulaEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.AulaJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AulaJpaAdapterTest {

    @Mock
    private AulaJpaRepository aulaJpaRepository;

    @InjectMocks
    private AulaJpaAdapter aulaJpaAdapter;

    @Test
    void guardar_debePersistirYRetornarDto() {
        CrearAulaDto request = new CrearAulaDto("Laboratorio", "Ciencias", 25, true);
        when(aulaJpaRepository.save(any(AulaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AulaDto result = aulaJpaAdapter.guardar(request);

        assertEquals("Laboratorio", result.nombre());
        assertEquals("Ciencias", result.descripcion());
        assertEquals(25, result.capacidad());
        assertTrue(result.activo());
        verify(aulaJpaRepository).save(any(AulaEntity.class));
    }

    @Test
    void actualizar_debeAplicarCambios() {
        UUID aulaId = UUID.randomUUID();
        AulaEntity entity = new AulaEntity();
        entity.setId(aulaId);
        entity.setNombre("Laboratorio");
        entity.setDescripcion("Ciencias");
        entity.setCapacidad(25);
        entity.setActivo(true);

        when(aulaJpaRepository.findById(aulaId)).thenReturn(Optional.of(entity));
        when(aulaJpaRepository.save(entity)).thenReturn(entity);

        ActualizarAulaDto request = new ActualizarAulaDto(aulaId, "Laboratorio 2", "Matemáticas", 30, false);
        AulaDto result = aulaJpaAdapter.actualizar(request);

        assertEquals("Laboratorio 2", result.nombre());
        assertEquals("Matemáticas", result.descripcion());
        assertEquals(30, result.capacidad());
        assertTrue(!result.activo());
        verify(aulaJpaRepository).save(entity);
    }

    @Test
    void existePorNombre_debeDelegarEnRepositorio() {
        when(aulaJpaRepository.existsByNombreIgnoreCase("Laboratorio")).thenReturn(true);

        boolean result = aulaJpaAdapter.existePorNombre("Laboratorio");

        assertTrue(result);
        verify(aulaJpaRepository).existsByNombreIgnoreCase("Laboratorio");
    }

    @Test
    void obtenerPorId_debeRetornarDto() {
        UUID aulaId = UUID.randomUUID();
        AulaEntity entity = new AulaEntity();
        entity.setId(aulaId);
        entity.setNombre("Laboratorio");
        entity.setActivo(true);

        when(aulaJpaRepository.findById(aulaId)).thenReturn(Optional.of(entity));

        AulaDto result = aulaJpaAdapter.obtenerPorId(aulaId);

        assertEquals(aulaId, result.id());
        assertEquals("Laboratorio", result.nombre());
        verify(aulaJpaRepository).findById(aulaId);
    }

    @Test
    void listar_debeRetornarTodasLasAulas() {
        AulaEntity entity = new AulaEntity();
        entity.setId(UUID.randomUUID());
        entity.setNombre("Laboratorio");
        entity.setActivo(true);

        when(aulaJpaRepository.findAll()).thenReturn(List.of(entity));

        List<AulaDto> result = aulaJpaAdapter.listar();

        assertEquals(1, result.size());
        assertEquals("Laboratorio", result.get(0).nombre());
        verify(aulaJpaRepository).findAll();
    }
}
