package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;
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
import java.util.concurrent.ThreadLocalRandom;


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
        CrearAulaDto request = CrearAulaDto.builder()
                .nombre("Laboratorio")
                .descripcion("Ciencias")
                .capacidad(25)
                .build();
        when(aulaJpaRepository.save(any(AulaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Aula result = aulaJpaAdapter.guardar(request);

        assertEquals("Laboratorio", result.nombre());
        assertEquals("Ciencias", result.descripcion());
        assertEquals(25, result.capacidad());
        verify(aulaJpaRepository).save(any(AulaEntity.class));
    }

    @Test
    void actualizar_debeAplicarCambios() {
        Long aulaId = ThreadLocalRandom.current().nextLong();
        AulaEntity entity = new AulaEntity();
        entity.setId(aulaId);
        entity.setNombre("Laboratorio");
        entity.setDescripcion("Ciencias");
        entity.setCapacidad(25);

        when(aulaJpaRepository.findById(aulaId)).thenReturn(Optional.of(entity));
        when(aulaJpaRepository.save(entity)).thenReturn(entity);

//        ActualizarAula request = new ActualizarAula(aulaId, "Laboratorio 2", "Matemáticas", 30, false);
//        Aula result = aulaJpaAdapter.actualizar(request);

//        assertEquals("Laboratorio 2", result.nombre());
//        assertEquals("Matemáticas", result.descripcion());
//        assertEquals(30, result.capacidad());
//        assertTrue(!result.eliminado());
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
        Long aulaId = ThreadLocalRandom.current().nextLong();
        AulaEntity entity = new AulaEntity();
        entity.setId(aulaId);
        entity.setNombre("Laboratorio");

        when(aulaJpaRepository.findById(aulaId)).thenReturn(Optional.of(entity));

        Aula result = aulaJpaAdapter.obtenerPorId(aulaId);

        assertEquals(aulaId, result.id());
        assertEquals("Laboratorio", result.nombre());
        verify(aulaJpaRepository).findById(aulaId);
    }

    @Test
    void listar_debeRetornarTodasLasAulas() {
        AulaEntity entity = new AulaEntity();
        entity.setId(ThreadLocalRandom.current().nextLong());
        entity.setNombre("Laboratorio");

        when(aulaJpaRepository.findAll()).thenReturn(List.of(entity));

        List<Aula> result = aulaJpaAdapter.listar();

        assertEquals(1, result.size());
        assertEquals("Laboratorio", result.get(0).nombre());
        verify(aulaJpaRepository).findAll();
    }
}
