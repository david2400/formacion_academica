package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ActualizarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ConsultarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.CrearGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.EliminarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ListarGradosUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;
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

@Description(value = "Gestiona los grados")
@Tag(name = "Grados", description = "Gestiona los grados")
@RestController
@RequestMapping("/estructura-institucion/grados")
public class GradoController {

    private final CrearGradoUseCase crearGradoUseCase;
    private final ActualizarGradoUseCase actualizarGradoUseCase;
    private final ConsultarGradoUseCase consultarGradoUseCase;
    private final ListarGradosUseCase listarGradosUseCase;
    private final EliminarGradoUseCase eliminarGradoUseCase;

    public GradoController(CrearGradoUseCase crearGradoUseCase,
                           ActualizarGradoUseCase actualizarGradoUseCase,
                           ConsultarGradoUseCase consultarGradoUseCase,
                           ListarGradosUseCase listarGradosUseCase,
                           EliminarGradoUseCase eliminarGradoUseCase) {
        this.crearGradoUseCase = crearGradoUseCase;
        this.actualizarGradoUseCase = actualizarGradoUseCase;
        this.consultarGradoUseCase = consultarGradoUseCase;
        this.listarGradosUseCase = listarGradosUseCase;
        this.eliminarGradoUseCase = eliminarGradoUseCase;
    }

    @PostMapping
    public ResponseEntity<GradoDto> crear(@RequestBody CrearGradoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearGradoUseCase.crear(request));
    }

    @PutMapping("/{gradoId}")
    public ResponseEntity<GradoDto> actualizar(@PathVariable UUID gradoId,
                                               @RequestBody ActualizarGradoDto request) {
        request.setId(gradoId);
        return ResponseEntity.ok(actualizarGradoUseCase.actualizar(request));
    }

    @GetMapping("/{gradoId}")
    public ResponseEntity<GradoDto> consultar(@PathVariable UUID gradoId) {
        return ResponseEntity.ok(consultarGradoUseCase.consultarPorId(gradoId));
    }

    @GetMapping
    public ResponseEntity<List<GradoDto>> listar() {
        return ResponseEntity.ok(listarGradosUseCase.listar());
    }

    @DeleteMapping("/{gradoId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID gradoId) {
        eliminarGradoUseCase.eliminar(gradoId);
        return ResponseEntity.noContent().build();
    }
}
