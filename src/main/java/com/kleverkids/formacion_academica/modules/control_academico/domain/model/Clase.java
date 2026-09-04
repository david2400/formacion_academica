package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.clase.EstadoClase;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clase {
    private Long id;
    private String codigo;
    private String nombre;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Long> profesoresIds;
    private Long tipoClaseId;
    private EstadoClase estado;
    /** Bitácora de la clase, en orden cronológico. */
    private List<ObservacionClase> observaciones;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
