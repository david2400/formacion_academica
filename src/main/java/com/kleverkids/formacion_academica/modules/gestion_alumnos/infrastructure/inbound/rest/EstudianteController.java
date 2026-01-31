package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ActualizarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ConsultarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.CrearEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.ActualizarEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gestion-alumnos/estudiantes")
public class EstudianteController {

    private final CrearEstudianteUseCase crearUseCase;
    private final ActualizarEstudianteUseCase actualizarUseCase;
    private final ConsultarEstudianteUseCase consultarUseCase;
    private final ListarEstudiantesUseCase listarUseCase;

    public EstudianteController(CrearEstudianteUseCase crearUseCase,
                                ActualizarEstudianteUseCase actualizarUseCase,
                                ConsultarEstudianteUseCase consultarUseCase,
                                ListarEstudiantesUseCase listarUseCase) {
        this.crearUseCase = crearUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarUseCase = listarUseCase;
    }

    @PostMapping
    public ResponseEntity<EstudianteDto> crear(@RequestBody CrearEstudianteDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @PutMapping("/{estudianteId}")
    public ResponseEntity<EstudianteDto> actualizar(@PathVariable UUID estudianteId,
                                                    @RequestBody ActualizarEstudianteDto request) {
        ActualizarEstudianteDto payload = new ActualizarEstudianteDto(
                estudianteId,
                request.tipoDocumento(),
                request.numeroDocumento(),
                request.nombres(),
                request.apellidos(),
                request.fechaNacimiento(),
                request.genero(),
                request.correo(),
                request.telefono(),
                request.direccion()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(payload));
    }

    @GetMapping("/{estudianteId}")
    public ResponseEntity<EstudianteDto> consultar(@PathVariable UUID estudianteId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(estudianteId));
    }

    @GetMapping
    public ResponseEntity<List<EstudianteDto>> listar() {
        return ResponseEntity.ok(listarUseCase.listar());
    }
}
