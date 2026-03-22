package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "estados")
@EqualsAndHashCode(callSuper = false)
public class EstadoEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid = UUID.randomUUID().toString();


    @Column(nullable = false, length = 50)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 7)
    private String color;

    @Column(length = 50)
    private String icono;

    @Column(name = "id_modulo", nullable = false)
    private Long idModulo;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "es_inicial", nullable = false)
    private Boolean esInicial = false;

    @Column(name = "es_final", nullable = false)
    private Boolean esFinal = false;

    @Column(nullable = false)
    private Integer orden = 0;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(columnDefinition = "JSON")
    private String metadata;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EntidadEstadoEntity> entidadesEstado;

    // Removed historial mapping as EstadoHistorialEntity doesn't have a 'estado' property
    // It has estadoAnterior and estadoNuevo instead

    @OneToMany(mappedBy = "estadoOrigen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EstadoTransicionEntity> transicionesOrigen;

    @OneToMany(mappedBy = "estadoDestino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EstadoTransicionEntity> transicionesDestino;


}
