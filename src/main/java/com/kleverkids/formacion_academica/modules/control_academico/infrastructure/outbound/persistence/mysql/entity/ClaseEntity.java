package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.clase.EstadoClase;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "clases")
public class ClaseEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    /** Inicio de la clase, con hora: una clase es una franja concreta. */
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    /** Fin de la clase. Si no se informa, el adapter usa el inicio. */
    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "clase_profesores", joinColumns = @JoinColumn(name = "clase_id"))
    @Column(name = "profesor_id")
    private List<Long> profesoresIds;

    /** Tipo de clase del catálogo (tipos_clase). Opcional. */
    @Column(name = "tipo_clase_id")
    private Long tipoClaseId;

    /** Seguimiento: si la clase se dictó, sigue programada o fue cancelada. */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20)
    private EstadoClase estado;

    /**
     * Bitácora de observaciones de la clase. Se agregan anotaciones a lo largo
     * del tiempo (cada una con fecha y autor); no se sobrescriben entre sí.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "clase_id")
    @OrderBy("createdAt ASC")
    private List<ObservacionClaseEntity> observaciones = new ArrayList<>();

    /** Agrega una anotación a la bitácora. */
    public void agregarObservacion(String texto) {
        if (texto == null || texto.isBlank()) return;
        if (observaciones == null) observaciones = new ArrayList<>();

        ObservacionClaseEntity observacion = new ObservacionClaseEntity();
        observacion.setObservacion(texto.trim());
        observaciones.add(observacion);
    }

}