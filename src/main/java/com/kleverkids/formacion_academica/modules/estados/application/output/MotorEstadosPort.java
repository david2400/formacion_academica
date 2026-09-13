package com.kleverkids.formacion_academica.modules.estados.application.output;

import com.kleverkids.formacion_academica.modules.estados.domain.model.TransicionMotor;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoMotor;

import java.util.List;

/**
 * Acceso al motor de máquinas de estados de access_control.
 *
 * <p>Los módulos de negocio dependen de esta interfaz, no de cómo se llega al motor.
 * Hoy la implementación es SQL directo contra el esquema {@code security}; antes fue
 * un cliente HTTP y mañana podría embeberse. Nada de eso asoma por aquí.
 *
 * <p><b>El motor es la fuente de verdad del estado.</b> La columna {@code estado_id}
 * de las tablas de negocio es una réplica que existe para poder listar y filtrar sin
 * salir a la red. Cuando ambos discrepan, manda el motor.
 */
public interface MotorEstadosPort {

    /**
     * Identificador del estado inicial de una máquina.
     *
     * <p>Se usa al crear una entidad, antes de que exista su id, para poblar
     * {@code estado_id} sin quemar un número en el mapper.
     *
     * @throws com.kleverkids.formacion_academica.modules.estados.domain.exception.MotorEstadosException
     *         si la máquina no existe, no está publicada o el motor no responde
     */
    Long estadoInicial(String maquina);

    /**
     * Todos los estados de una máquina, ordenados.
     *
     * <p>Lo consume la UI para pintar etiquetas y colores: con solo el
     * {@code estado_id} guardado en la tabla de negocio no se puede mostrar nada
     * legible.
     */
    List<EstadoMotor> estadosDe(String maquina);

    /**
     * El grafo completo de la máquina: desde qué estado, con qué acción, hacia cuál.
     *
     * <p>Se expone el grafo entero y no "los destinos de esta entidad" porque las
     * pantallas son tablas: lo segundo obligaría a una petición por fila. Con esto, el
     * cliente calcula los destinos de cualquier fila a partir del {@code estado_id}
     * que ya trae, y deja de ofrecer cambios que el backend va a rechazar.
     */
    List<TransicionMotor> transicionesDe(String maquina);

    /**
     * Arranca el ciclo de vida de una entidad recién creada.
     *
     * <p>Debe llamarse <b>después</b> de persistir, porque el motor necesita el id
     * definitivo. Es idempotente: repetirlo sobre una entidad que ya tiene instancia
     * no crea una segunda.
     */
    void iniciarCiclo(String maquina, String tipoEntidad, Long entidadId);

    /**
     * Lleva una entidad hasta un estado concreto y devuelve el estado resultante.
     *
     * <p>El motor razona en acciones, no en estados destino; esta operación traduce
     * entre ambos mundos resolviendo qué acción disponible lleva a ese estado desde
     * el actual. Se mantiene así porque la API pública de formacion_academica y el
     * frontend ya hablan de {@code nuevo_estado_id}, y cambiar ese contrato es una
     * decisión aparte.
     *
     * @return el id del estado en que quedó la entidad, para replicarlo en la tabla
     * @throws com.kleverkids.formacion_academica.modules.estados.domain.exception.MotorEstadosException
     *         si no hay ninguna acción que lleve a ese estado, si el motor la rechaza
     *         por reglas o permisos, o si no responde
     */
    Long moverAEstado(String maquina, String tipoEntidad, Long entidadId,
                      Long estadoDestino, String motivo);
}
