package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ConsultarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ListarMatriculasUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.RegistrarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Description(value = "Gestiona las matriculas de los alumnos")
@Tag(name = "Matricula")
@RestController
@RequestMapping("/api/admisiones/matriculas")
public class MatriculaController {

    private final RegistrarMatriculaUseCase registrarUseCase;
    private final ConsultarMatriculaUseCase consultarUseCase;
    private final ListarMatriculasUseCase listarUseCase;

    public MatriculaController(RegistrarMatriculaUseCase registrarUseCase,
                               ConsultarMatriculaUseCase consultarUseCase,
                               ListarMatriculasUseCase listarUseCase) {
        this.registrarUseCase = registrarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarUseCase = listarUseCase;
    }

    @PostMapping
    public ResponseEntity<MatriculaDto> registrar(@RequestBody CrearMatriculaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarUseCase.registrar(request));
    }

    @GetMapping("/{matriculaId}")
    public ResponseEntity<MatriculaDto> consultar(@PathVariable UUID matriculaId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(matriculaId));
    }

    @GetMapping
    public ResponseEntity<List<MatriculaDto>> listarPorEstudiante(@RequestParam UUID estudianteId) {
        return ResponseEntity.ok(listarUseCase.listarPorEstudiante(estudianteId));
    }
}
