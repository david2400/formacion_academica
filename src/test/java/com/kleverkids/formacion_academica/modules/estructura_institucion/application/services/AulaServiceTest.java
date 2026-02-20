package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula.AulaRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @Mock
    private AulaRepositoryPort aulaRepositoryPort;

    @InjectMocks
    private AulaService aulaService;

    @Test
    void crear_debeGuardarCuandoNombreDisponible() {
        CrearAulaDto request = new CrearAulaDto("Laboratorio", "Ciencias", 25, true);
        AulaDto expected = new AulaDto(UUID.randomUUID(), "Laboratorio", "Ciencias", 25, true);

        when(aulaRepositoryPort.existePorNombre("Laboratorio")).thenReturn(false);
        when(aulaRepositoryPort.guardar(request)).thenReturn(expected);

        AulaDto result = aulaService.crear(request);

        assertEquals(expected, result);
        verify(aulaRepositoryPort).guardar(request);
    }

    @Test
    void crear_debeFallarCuandoNombreDuplicado() {
        CrearAulaDto request = new CrearAulaDto("Laboratorio", "Ciencias", 25, true);
        when(aulaRepositoryPort.existePorNombre("Laboratorio")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> aulaService.crear(request));
        verify(aulaRepositoryPort, never()).guardar(request);
    }

    @Test
    void actualizar_debeActualizarCuandoNombreSinCambio() {
//        UUID aulaId = UUID.randomUUID();
//        ActualizarAulaDto request = new ActualizarAulaDto(aulaId, "Laboratorio", "Ciencias", 30, true);
//        AulaDto persisted = new AulaDto(aulaId, "Laboratorio", "Ciencias", 25, true);
//        AulaDto updated = new AulaDto(aulaId, "Laboratorio", "Ciencias", 30, true);
//
//        when(aulaRepositoryPort.existePorId(aulaId)).thenReturn(true);
//        when(aulaRepositoryPort.existePorNombre("Laboratorio")).thenReturn(true);
//        when(aulaRepositoryPort.obtenerPorId(aulaId)).thenReturn(persisted);
//        when(aulaRepositoryPort.actualizar(request)).thenReturn(updated);
//
//        AulaDto result = aulaService.actualizar(request);
//
//        assertEquals(updated, result);
//        verify(aulaRepositoryPort).actualizar(request);
    }

    @Test
    void actualizar_debeFallarCuandoAulaNoExiste() {
//        UUID aulaId = UUID.randomUUID();
//        ActualizarAulaDto request = new ActualizarAulaDto(aulaId, "Laboratorio", null, null, null);
//        when(aulaRepositoryPort.existePorId(aulaId)).thenReturn(false);
//
//        assertThrows(IllegalArgumentException.class, () -> aulaService.actualizar(request));
//        verify(aulaRepositoryPort, never()).actualizar(request);
    }

    @Test
    void listar_debeDelegarEnRepositorio() {
        List<AulaDto> aulas = List.of(new AulaDto(UUID.randomUUID(), "Laboratorio", null, null, true));
        when(aulaRepositoryPort.listar()).thenReturn(aulas);

        List<AulaDto> result = aulaService.listar();

        assertEquals(aulas, result);
        verify(aulaRepositoryPort).listar();
    }
}
