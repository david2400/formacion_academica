package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ActualizarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ConsultarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.CrearEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.EliminarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesPaginadoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@Description(value = "Gestiona los estudiantes")
@Tag(name = "Estudiantes", description = "Gestiona los estudiantes")
@RestController
@RequestMapping("/gestion-alumnos/estudiantes")
public class EstudianteController {

    private final CrearEstudianteUseCase crearUseCase;
    private final ActualizarEstudianteUseCase actualizarUseCase;
    private final ConsultarEstudianteUseCase consultarUseCase;
    private final ListarEstudiantesUseCase listarUseCase;
    private final ListarEstudiantesPaginadoUseCase listarPaginadoUseCase;
    private final EliminarEstudianteUseCase eliminarUseCase;

    public EstudianteController(CrearEstudianteUseCase crearUseCase,
                                ActualizarEstudianteUseCase actualizarUseCase,
                                ConsultarEstudianteUseCase consultarUseCase,
                                ListarEstudiantesUseCase listarUseCase,
                                ListarEstudiantesPaginadoUseCase listarPaginadoUseCase,
                                EliminarEstudianteUseCase eliminarUseCase) {
        this.crearUseCase = crearUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarUseCase = listarUseCase;
        this.listarPaginadoUseCase = listarPaginadoUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @PostMapping
    public ResponseEntity<EstudianteDto> crear(@Valid @RequestBody CrearEstudianteDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @PutMapping("/{estudianteId}")
    public ResponseEntity<EstudianteDto> actualizar(@PathVariable UUID estudianteId,
                                                    @Valid @RequestBody UpdateEstudianteDto request) {
        request.setEstudianteId(estudianteId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @GetMapping("/{estudianteId}")
    public ResponseEntity<EstudianteDto> consultar(@PathVariable UUID estudianteId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(estudianteId));
    }

    @GetMapping
    public ResponseEntity<List<EstudianteDto>> listar() {
        return ResponseEntity.ok(listarUseCase.listar());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<EstudianteDto>> listar(Pageable pageable) {
        return ResponseEntity.ok(listarPaginadoUseCase.listar(pageable));
    }

    @DeleteMapping("/{estudianteId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID estudianteId) {
        eliminarUseCase.eliminar(estudianteId);
        return ResponseEntity.noContent().build();
    }
}
