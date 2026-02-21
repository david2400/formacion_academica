package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ActualizarAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ConsultarAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.CrearAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.EliminarAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ListarAulasUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
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

@Description(value = "Gestiona las aulas")
@Tag(name = "Aulas", description = "Gestiona las aulas")
@RestController
@RequestMapping("/estructura-institucion/aulas")
public class AulaController {

    private final CrearAulaUseCase crearAulaUseCase;
    private final ActualizarAulaUseCase actualizarAulaUseCase;
    private final ListarAulasUseCase listarAulasUseCase;
    private final ConsultarAulaUseCase consultarAulaUseCase;
    private final EliminarAulaUseCase eliminarAulaUseCase;

    public AulaController(CrearAulaUseCase crearAulaUseCase,
                          ActualizarAulaUseCase actualizarAulaUseCase,
                          ListarAulasUseCase listarAulasUseCase,
                          ConsultarAulaUseCase consultarAulaUseCase,
                          EliminarAulaUseCase eliminarAulaUseCase) {
        this.crearAulaUseCase = crearAulaUseCase;
        this.actualizarAulaUseCase = actualizarAulaUseCase;
        this.listarAulasUseCase = listarAulasUseCase;
        this.consultarAulaUseCase = consultarAulaUseCase;
        this.eliminarAulaUseCase = eliminarAulaUseCase;
    }

    @PostMapping
    public ResponseEntity<AulaDto> crear(@RequestBody CrearAulaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearAulaUseCase.crear(request));
    }

    @PutMapping("/{aulaId}")
    public ResponseEntity<AulaDto> actualizar(@PathVariable UUID aulaId,
                                              @RequestBody ActualizarAulaDto request) {
        request.setId(aulaId);
        return ResponseEntity.ok(actualizarAulaUseCase.actualizar(request));
    }

    @GetMapping
    public ResponseEntity<List<AulaDto>> listar() {
        return ResponseEntity.ok(listarAulasUseCase.listar());
    }

    @GetMapping("/{aulaId}")
    public ResponseEntity<AulaDto> consultar(@PathVariable UUID aulaId) {
        return ResponseEntity.ok(consultarAulaUseCase.consultarPorId(aulaId));
    }

    @DeleteMapping("/{aulaId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID aulaId) {
        eliminarAulaUseCase.eliminar(aulaId);
        return ResponseEntity.noContent().build();
    }
}
