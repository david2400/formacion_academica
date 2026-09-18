package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearAcudienteDto {
    
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "El nombre del acudiente es obligatorio")
    private String nombres;
    private String apellidos;

    @NotBlank(message = "El parentesco es obligatorio")
    private String parentesco;
    private String telefono;

    @Email(message = "El correo debe ser válido")
    private String correo;
    private boolean esPrincipal;

    /**
     * Id del User en access_control que representa a este acudiente (ya trae
     * embebida la identidad de su Client). Opcional por ahora (compatibilidad
     * con integraciones existentes); cuando se informa, el servicio valida que
     * exista y esté asignado a {@code empresaId}.
     */
    private Long usuarioId;

    /**
     * Empresa (tenant) a la que pertenece este acudiente. Obligatoria si se
     * informa {@code usuarioId}, para poder validarlo contra access_control.
     */
    private Long empresaId;

    /**
     * Contraseña propia del acudiente, en texto plano tal como la envía el
     * cliente. El servicio la hashea (BCrypt) antes de persistir — nunca se
     * guarda ni se devuelve en texto plano. Opcional: si no se informa, no se
     * modifica la contraseña existente (en una actualización) o el acudiente
     * queda sin contraseña (en una creación).
     */
    private String password;

}
