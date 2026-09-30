package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "estudiantes")
public class EstudianteEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, unique = true)
    private String numeroDocumento;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido", nullable = false)
    private String segundoApellido;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "genero", nullable = false)
    private String genero;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "pais_residencia_id", length = 50)
    private String paisResidenciaId;

    @Column(name = "departamento_residencia_id", length = 50)
    private String departamentoResidenciaId;

    @Column(name = "ciudad_residencia_id", length = 50)
    private String ciudadResidenciaId;

    @Column(name = "pais_nacimiento_id", length = 50)
    private String paisNacimientoId;

    @Column(name = "departamento_nacimiento_id", length = 50)
    private String departamentoNacimientoId;

    @Column(name = "ciudad_nacimiento_id", length = 50)
    private String ciudadNacimientoId;

    @Column(name = "empresa_id")
    private Long empresaId;

    /** Hash BCrypt de la contraseña propia del estudiante. Nunca texto plano. */
    @Column(name = "password")
    private String password;
}
