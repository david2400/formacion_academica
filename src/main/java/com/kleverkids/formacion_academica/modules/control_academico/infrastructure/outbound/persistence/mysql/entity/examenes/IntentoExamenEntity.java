package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.RespuestaIntentoEntity;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "intentos_examen")
public class IntentoExamenEntity extends AuditInfo {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long examenId;

    @Column(nullable = false)
    private Long estudianteId;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime iniciadoEn;

    @Column(nullable = false)
    private LocalDateTime finalizadoEn;

    @Column(nullable = false)
    private Integer puntajeTotal;

    @OneToMany(mappedBy = "intento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RespuestaIntentoEntity> respuestas = new ArrayList<>();

}
