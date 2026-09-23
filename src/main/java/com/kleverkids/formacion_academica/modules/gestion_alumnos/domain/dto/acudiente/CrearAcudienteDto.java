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

    private String telefono;

    @Email(message = "El correo debe ser válido")
    private String correo;
    /**
     * Empresa (tenant) a la que pertenece este acudiente. Si existe una
     * persona en access_control con el documento indicado, el servicio valida
     * que esté asignada a esta empresa.
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
