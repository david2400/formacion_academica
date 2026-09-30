package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "El país de residencia es obligatorio")
    @Size(max = 50, message = "El país de residencia no puede exceder 50 caracteres")
    private String paisResidenciaId;

    @NotBlank(message = "El departamento de residencia es obligatorio")
    @Size(max = 50, message = "El departamento de residencia no puede exceder 50 caracteres")
    private String departamentoResidenciaId;

    @NotBlank(message = "La ciudad de residencia es obligatoria")
    @Size(max = 50, message = "La ciudad de residencia no puede exceder 50 caracteres")
    private String ciudadResidenciaId;

    @NotBlank(message = "El país de nacimiento es obligatorio")
    @Size(max = 50, message = "El país de nacimiento no puede exceder 50 caracteres")
    private String paisNacimientoId;

    @NotBlank(message = "El departamento de nacimiento es obligatorio")
    @Size(max = 50, message = "El departamento de nacimiento no puede exceder 50 caracteres")
    private String departamentoNacimientoId;

    @NotBlank(message = "La ciudad de nacimiento es obligatoria")
    @Size(max = 50, message = "La ciudad de nacimiento no puede exceder 50 caracteres")
    private String ciudadNacimientoId;

    /**
     * Empresa (tenant) a la que pertenece este estudiante. Si existe una
     * persona en access_control con el documento indicado, el servicio valida
     * que esté asignada a esta empresa.
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
