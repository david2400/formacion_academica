package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ActualizarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ConsultarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.CrearAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.EliminarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ListarAcudientesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Description(value = "Gestiona los acudientes")
@Tag(name = "Acudientes", description = "Gestiona los acudientes")
@RestController
@RequestMapping("/gestion-alumnos/acudientes")
public class AcudienteController {

    private final CrearAcudienteUseCase crearUseCase;
    private final ActualizarAcudienteUseCase actualizarUseCase;
    private final ConsultarAcudienteUseCase consultarUseCase;
    private final ListarAcudientesPorEstudianteUseCase listarUseCase;
    private final EliminarAcudienteUseCase eliminarUseCase;

    public AcudienteController(CrearAcudienteUseCase crearUseCase,
                               ActualizarAcudienteUseCase actualizarUseCase,
                               ConsultarAcudienteUseCase consultarUseCase,
                               ListarAcudientesPorEstudianteUseCase listarUseCase,
                               EliminarAcudienteUseCase eliminarUseCase) {
        this.crearUseCase = crearUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarUseCase = listarUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @PostMapping
    public ResponseEntity<AcudienteDto> crear(@RequestBody CrearAcudienteDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @PutMapping("/{acudienteId}")
    public ResponseEntity<AcudienteDto> actualizar(@PathVariable UUID acudienteId,
                                                   @RequestBody ActualizarAcudienteDto request) {
        ActualizarAcudienteDto payload = new ActualizarAcudienteDto(
                acudienteId,
                request.estudianteId(),
                request.tipoDocumento(),
                request.numeroDocumento(),
                request.nombres(),
                request.apellidos(),
                request.parentesco(),
                request.telefono(),
                request.correo(),
                request.esPrincipal()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(payload));
    }

    @GetMapping("/{acudienteId}")
    public ResponseEntity<AcudienteDto> consultar(@PathVariable UUID acudienteId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(acudienteId));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<AcudienteDto>> listarPorEstudiante(@PathVariable UUID estudianteId) {
        return ResponseEntity.ok(listarUseCase.listar(estudianteId));
    }

    @DeleteMapping("/{acudienteId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID acudienteId) {
        eliminarUseCase.eliminar(acudienteId);
        return ResponseEntity.noContent().build();
    }
}
