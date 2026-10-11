package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback;

import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoMotor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Traduce entre los {@code estado_id} replicados en las tablas y los CÓDIGOS
 * de estado del motor de access_control (ver V8__state_machines_feedback_clases.sql).
 *
 * <p>Las reglas de negocio preguntan por código ("¿está PUBLICADO?"), nunca por
 * id: los ids los genera el motor y cambian entre entornos. Nombres, colores y
 * transiciones siguen siendo parametrizables en el motor; lo único que el
 * código conoce son los códigos, que son el contrato (igual que V4 respeta
 * los códigos de los enums existentes).
 */
@Component
@RequiredArgsConstructor
public class FeedbackEstados {

    public static final String MAQUINA_FORM = "FEEDBACK_FORM_LIFECYCLE";
    public static final String TIPO_ENTIDAD_FORM = "FEEDBACK_FORM";
    public static final String MAQUINA_APLICACION = "FEEDBACK_APLICACION_LIFECYCLE";
    public static final String TIPO_ENTIDAD_APLICACION = "FEEDBACK_APLICACION";
    /** Máquina de estudiantes_grupo (estructura_institucion), solo lectura. */
    public static final String MAQUINA_MEMBRESIA = "ASIGNACION_GRUPO_LIFECYCLE";

    public static final String BORRADOR = "BORRADOR";
    public static final String PUBLICADO = "PUBLICADO";
    public static final String ARCHIVADO = "ARCHIVADO";
    public static final String ACTIVA = "ACTIVA";
    public static final String CERRADA = "CERRADA";
    public static final String CANCELADA = "CANCELADA";
    /** Membresía que ya no da derecho a responder (retiro del grupo). */
    public static final String RETIRADO = "RETIRADO";

    private final MotorEstadosPort motor;

    /** Código del estado, o null si el id no pertenece a la máquina. */
    public String codigo(String maquina, Long estadoId) {
        if (estadoId == null) {
            return null;
        }
        return motor.estadosDe(maquina).stream()
                .filter(e -> Objects.equals(e.id(), estadoId))
                .map(EstadoMotor::codigo)
                .findFirst()
                .orElse(null);
    }

    public boolean es(String maquina, Long estadoId, String codigo) {
        return codigo.equals(codigo(maquina, estadoId));
    }

    /** Ids de los estados con esos códigos (los que no existan en el motor se ignoran). */
    public List<Long> ids(String maquina, String... codigos) {
        Set<String> buscados = Set.copyOf(Arrays.asList(codigos));
        return motor.estadosDe(maquina).stream()
                .filter(e -> buscados.contains(e.codigo()))
                .map(EstadoMotor::id)
                .toList();
    }
}
