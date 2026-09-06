package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Asignación de un estudiante a un grupo.
 *
 * <p>{@code estadoId} replica el estado que decide el motor. El cliente resuelve el nombre
 * y el color consultando {@code /estados/contextos/estudiante_grupo}, de modo que
 * agregar o renombrar un estado no exige tocar código ni en el backend ni en el front.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteGrupo {
    private Long id;
    private Long estudianteId;
    private Long grupoId;
    private LocalDate fechaAsignacion;
    private Long estadoId;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
