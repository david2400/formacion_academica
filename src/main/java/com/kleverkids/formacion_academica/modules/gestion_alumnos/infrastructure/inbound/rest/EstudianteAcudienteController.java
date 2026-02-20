package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ActualizarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ConsultarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.CrearEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ListarPorAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ListarPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Description(value = "Gestiona la relación entre estudiantes y acudientes")
@Tag(name = "Estudiante - Acudiente", description = "Gestiona la relación entre estudiantes y acudientes")
@RestController
@RequestMapping("/gestion-alumnos/relaciones-estudiante-acudiente")
public class EstudianteAcudienteController {

    private final CrearEstudianteAcudienteUseCase crearUseCase;
    private final ActualizarEstudianteAcudienteUseCase actualizarUseCase;
    private final ConsultarEstudianteAcudienteUseCase consultarUseCase;
    private final ListarPorEstudianteUseCase listarPorEstudianteUseCase;
    private final ListarPorAcudienteUseCase listarPorAcudienteUseCase;

    public EstudianteAcudienteController(CrearEstudianteAcudienteUseCase crearUseCase,
                                         ActualizarEstudianteAcudienteUseCase actualizarUseCase,
                                         ConsultarEstudianteAcudienteUseCase consultarUseCase,
                                         ListarPorEstudianteUseCase listarPorEstudianteUseCase,
                                         ListarPorAcudienteUseCase listarPorAcudienteUseCase) {
        this.crearUseCase = crearUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarPorEstudianteUseCase = listarPorEstudianteUseCase;
        this.listarPorAcudienteUseCase = listarPorAcudienteUseCase;
    }

    @PostMapping
    public ResponseEntity<EstudianteAcudienteDto> crear(@RequestBody CrearEstudianteAcudienteDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @PutMapping("/{relacionId}")
    public ResponseEntity<EstudianteAcudienteDto> actualizar(@PathVariable UUID relacionId,
                                                                     @RequestBody ActualizarEstudianteAcudienteDto request) {
        ActualizarEstudianteAcudienteDto payload = new ActualizarEstudianteAcudienteDto(
                relacionId,
                request.parentesco(),
                request.esPrincipal(),
                request.estado()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(payload));
    }

    @GetMapping("/{relacionId}")
    public ResponseEntity<EstudianteAcudienteDto> consultar(@PathVariable UUID relacionId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(relacionId));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<EstudianteAcudienteDto>> listarPorEstudiante(@PathVariable UUID estudianteId) {
        return ResponseEntity.ok(listarPorEstudianteUseCase.listarPorEstudiante(estudianteId));
    }

    @GetMapping
    public ResponseEntity<List<EstudianteAcudienteDto>> listarPorAcudiente(@RequestParam UUID acudienteId) {
        return ResponseEntity.ok(listarPorAcudienteUseCase.listarPorAcudiente(acudienteId));
    }
}
