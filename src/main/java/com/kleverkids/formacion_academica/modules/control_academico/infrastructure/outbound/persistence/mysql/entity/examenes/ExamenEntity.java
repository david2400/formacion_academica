package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "examenes")
public class ExamenEntity extends AuditInfo {

    @Id
    private Long id;

    // @Column(name = "clase_id", nullable = false)
    // private Long claseId;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    // @Column(name = "fecha", nullable = false)
    // private LocalDate fecha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "examen_reglas", joinColumns = @JoinColumn(name = "examen_id"))
    private List<ReglaCalificacionEmbeddable> reglas;

    @Embeddable
    public static class ReglaCalificacionEmbeddable {

        private String criterio;
        private BigDecimal ponderacion;
        private BigDecimal puntajeMaximo;

        public String getCriterio() {
            return criterio;
        }

        public void setCriterio(String criterio) {
            this.criterio = criterio;
        }

        public BigDecimal getPonderacion() {
            return ponderacion;
        }

        public void setPonderacion(BigDecimal ponderacion) {
            this.ponderacion = ponderacion;
        }

        public BigDecimal getPuntajeMaximo() {
            return puntajeMaximo;
        }

        public void setPuntajeMaximo(BigDecimal puntajeMaximo) {
            this.puntajeMaximo = puntajeMaximo;
        }
    }
}
