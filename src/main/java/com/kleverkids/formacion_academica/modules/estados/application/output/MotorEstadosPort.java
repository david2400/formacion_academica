package com.kleverkids.formacion_academica.modules.estados.application.output;

/**
 * Acceso al motor de máquinas de estados de access_control.
 *
 * <p>Los módulos de negocio dependen de esta interfaz, no del cliente HTTP. Eso
 * mantiene los adaptadores JPA ignorantes de que el estado vive en otro servicio:
 * si mañana el motor se embebe, se cambia la implementación y nada más.
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
     * Si un estado pertenece a la máquina indicada.
     *
     * <p>Evita que llegue por la API un {@code estado_id} de otro ciclo de vida. No
     * dice si la transición es válida —eso solo lo sabe el motor conociendo el
     * estado actual—, solo que el estado existe en ese grafo.
     */
    boolean perteneceALaMaquina(String maquina, Long estadoId);

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
