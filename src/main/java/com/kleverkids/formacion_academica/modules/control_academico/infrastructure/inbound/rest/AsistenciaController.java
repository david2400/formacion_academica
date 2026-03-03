package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ActualizarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ConsultarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ConsultarHistorialAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.EliminarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ListarAsistenciasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.RegistrarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaFiltroDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.RegistrarAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.ActualizarAsistenciaDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Description(value = "Gestiona la asistencia de los estudiantes")
@Tag(name = "Asistencias", description = "Gestiona la asistencia de los estudiantes")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/asistencias")
public class AsistenciaController {

    private final ActualizarAsistenciaUseCase actualizarUseCase;
    private final ConsultarAsistenciaUseCase consultarUseCase;
    private final EliminarAsistenciaUseCase eliminarUseCase;
    private final ListarAsistenciasUseCase listarUseCase;
    private final RegistrarAsistenciaUseCase registrarAsistenciaUseCase;
    private final ConsultarHistorialAsistenciaUseCase historialAsistenciaUseCase;

    @PostMapping
    public ResponseEntity<AsistenciaDto> registrar(@Valid @RequestBody RegistrarAsistenciaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarAsistenciaUseCase.registrar(request));
    }

    @GetMapping("/{asistenciaId}")
    public ResponseEntity<AsistenciaDto> consultar(@PathVariable UUID asistenciaId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(asistenciaId));
    }

    @GetMapping
    public ResponseEntity<List<AsistenciaDto>> listar() {
        return ResponseEntity.ok(listarUseCase.listar());
    }

    @PutMapping("/{asistenciaId}")
    public ResponseEntity<AsistenciaDto> actualizar(@PathVariable UUID asistenciaId,
                                                    @RequestBody ActualizarAsistenciaDto request) {
        request = new ActualizarAsistenciaDto(
            asistenciaId,
            request.estudianteId(),
            request.claseId(),
            request.fecha(),
            request.estado(),
            request.observaciones()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @DeleteMapping("/{asistenciaId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID asistenciaId) {
        eliminarUseCase.eliminar(asistenciaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/historial")
    public ResponseEntity<HistorialAsistenciaDto> consultarHistorial(@RequestBody HistorialAsistenciaFiltroDto filtro) {
        return ResponseEntity.ok(historialAsistenciaUseCase.consultar(filtro));
    }
}
