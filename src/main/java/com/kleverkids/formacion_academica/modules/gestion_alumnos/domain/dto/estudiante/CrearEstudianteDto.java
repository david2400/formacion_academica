package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearEstudianteDto {

    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    private String nombres;

    @NotBlank(message = "El apellido del estudiante es obligatorio")
    private String primerApellido;

    @NotBlank(message = "El apellido del estudiante es obligatorio")
    private String segundoApellido;

    private LocalDate fechaNacimiento;
    private String genero;

    @Email(message = "El correo debe ser válido")
    private String correo;
    private String telefono;
    private String direccion;

    /**
     * Id del User en access_control que representa a este estudiante (ya trae
     * embebida la identidad de su Client). Opcional por ahora (compatibilidad
     * con integraciones existentes); cuando se informa, el servicio valida que
     * exista y esté asignado a {@code empresaId}.
     */
    private Long usuarioId;

    /**
     * Empresa (tenant) a la que pertenece este estudiante. Obligatoria si se
     * informa {@code usuarioId}, para poder validarlo contra access_control.
     */
    private Long empresaId;

    /**
     * Contraseña propia del estudiante, en texto plano tal como la envía el
     * cliente. El servicio la hashea (BCrypt) antes de persistir — nunca se
     * guarda ni se devuelve en texto plano. Opcional: si no se informa, no se
     * modifica la contraseña existente (en una actualización) o el estudiante
     * queda sin contraseña (en una creación).
     */
    private String password;
}
