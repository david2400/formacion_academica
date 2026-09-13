package com.kleverkids.formacion_academica.modules.estados.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import com.kleverkids.formacion_academica.modules.estados.domain.model.TransicionMotor;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoMotor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Lectura de los ciclos de vida, para que el frontend pueda pintarlos.
 *
 * <p>Es de <b>solo lectura</b> a propósito. Los estados y sus transiciones se
 * configuran en access_control —por migración o por su API— y no desde aquí:
 * duplicar la administración sería tener dos sitios donde tocar lo mismo.
 *
 * <p>Que exista este controlador y no que draco hable directamente con
 * access_control es deliberado: el frontend sigue con un solo backend, y el
 * conjunto entero sigue funcionando aunque la aplicación access_control esté
 * caída, porque esto lee su esquema por SQL.
 */
@Tag(name = "Estados", description = "Ciclos de vida disponibles y transiciones posibles")
@RestController
@RequestMapping("/estados")
public class EstadosController {

    private final MotorEstadosPort motorEstados;

    public EstadosController(MotorEstadosPort motorEstados) {
        this.motorEstados = motorEstados;
    }

    @Operation(summary = "Estados de una máquina",
            description = "Todos los estados con su nombre y color, ordenados. Es lo que necesita "
                    + "la UI para traducir un estado_id en algo legible.")
    @GetMapping("/maquinas/{maquina}")
    public ResponseEntity<List<EstadoMotor>> estadosDe(@PathVariable String maquina) {
        return ResponseEntity.ok(motorEstados.estadosDe(maquina));
    }

    @Operation(summary = "Grafo de transiciones de la máquina",
            description = "Desde qué estado, con qué acción, hacia cuál. La UI lo usa para ofrecer "
                    + "solo los cambios posibles desde el estado de cada fila, y para pedir el motivo "
                    + "antes de enviar cuando la transición lo exige. Se devuelve el grafo entero y no "
                    + "los destinos de una entidad porque las pantallas son tablas: lo segundo sería "
                    + "una petición por fila.")
    @GetMapping("/maquinas/{maquina}/transiciones")
    public ResponseEntity<List<TransicionMotor>> transiciones(@PathVariable String maquina) {
        return ResponseEntity.ok(motorEstados.transicionesDe(maquina));
    }
}
