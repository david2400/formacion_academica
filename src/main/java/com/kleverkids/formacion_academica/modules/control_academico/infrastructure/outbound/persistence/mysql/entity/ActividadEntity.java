package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "actividades")
@EqualsAndHashCode(callSuper = true)
public class ActividadEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String instrucciones;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoActividadEntity tipo;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private ContenidoActividadEntity contenido;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private ConfiguracionActividadEntity configuracion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoActividadEntity estado = EstadoActividadEntity.BORRADOR;

    @Column(name = "curso_id", nullable = false)
    private Long cursoId;

    @Column(name = "modulo_id")
    private Long moduloId;

    @Column(name = "orden")
    private Integer orden;

    @ElementCollection
    @CollectionTable(
        name = "actividad_dependencias",
        joinColumns = @JoinColumn(name = "actividad_id")
    )
    @Column(name = "dependencia_id")
    private List<Long> actividadesDependientes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private List<RecursoActividadEntity> recursos;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private EvaluacionConfiguracionEntity evaluacion;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private ProgresoActividadEntity progreso;

    // Enums para mapeo
    public enum TipoActividadEntity {
        LECTURA, VIDEO, CUESTIONARIO, TAREA_ENTREGABLE, 
        ACTIVIDAD_INTERACTIVA, FORO, PROYECTO, EXAMEN
    }

    public enum EstadoActividadEntity {
        BORRADOR, ACTIVA, PAUSADA, BLOQUEADA, FINALIZADA
    }

    // Sub-entidades JSON
    @Data
    public static class ContenidoActividadEntity {
        private String tipo;
        private Object datos;
        private List<RecursoContenidoEntity> recursos;
        private MetadatosContenidoEntity metadatos;
    }

    @Data
    public static class RecursoContenidoEntity {
        private String id;
        private String nombre;
        private String tipo;
        private String url;
        private Long tamañoBytes;
        private String formato;
    }

    @Data
    public static class MetadatosContenidoEntity {
        private String formato;
        private Integer duracionEstimada;
        private String idioma;
        private String nivelDificultad;
        private List<String> etiquetas;
    }

    @Data
    public static class ConfiguracionActividadEntity {
        private Boolean obligatoria = true;
        private Integer intentosPermitidos = 1;
        private Integer tiempoLimiteSegundos;
        private Boolean mostrarRetroalimentacion = true;
        private Boolean permiteRevisar = false;
        private List<ReglaAccesoEntity> reglasAcceso;
        private ConfiguracionNotificacionEntity notificaciones;
        private PoliticaSeguimientoEntity politicaSeguimiento;
    }

    @Data
    public static class ReglaAccesoEntity {
        private String tipo;
        private String condicion;
        private Object valor;
        private Boolean requerida;
    }

    @Data
    public static class ConfiguracionNotificacionEntity {
        private Boolean notificarInicio = true;
        private Boolean notificarCompletado = true;
        private Boolean notificarVencimiento = true;
        private Integer recordatorioHorasAntes = 24;
        private List<String> destinatarios;
    }

    @Data
    public static class PoliticaSeguimientoEntity {
        private Boolean registrarTiempo = true;
        private Boolean registrarInteracciones = true;
        private Boolean registrarProgreso = true;
        private Integer frecuenciaGuardadoSegundos = 30;
    }

    @Data
    public static class RecursoActividadEntity {
        private String id;
        private String nombre;
        private String descripcion;
        private String tipo;
        private String url;
        private Long tamañoBytes;
        private String formato;
        private Integer orden;
        private Boolean obligatorio = false;
        private LocalDateTime fechaDisponible;
        private LocalDateTime fechaExpiracion;
        private ConfiguracionAccesoEntity configuracionAcceso;
        private MetadatosRecursoEntity metadatos;
    }

    @Data
    public static class ConfiguracionAccesoEntity {
        private Boolean permiteDescarga = true;
        private Boolean permiteVisualizacionEnLinea = true;
        private Integer maximoVisualizaciones;
        private Integer tiempoAccesoMinutos;
        private List<String> formatosPermitidos;
    }

    @Data
    public static class MetadatosRecursoEntity {
        private String autor;
        private LocalDateTime fechaCreacion;
        private LocalDateTime fechaUltimaModificacion;
        private String idioma = "es";
        private Integer duracion;
        private String calidad;
        private Integer paginas;
        private String resolucion;
        private List<String> etiquetas;
    }

    @Data
    public static class EvaluacionConfiguracionEntity {
        private String tipo = "CUANTITATIVA";
        private Boolean automatica = true;
        private Double notaAprobacion = 70.0;
        private Double pesoTotal = 100.0;
        private List<CriterioEvaluacionEntity> criterios;
        private ConfiguracionRetroalimentacionEntity retroalimentacion;
        private PoliticaCalificacionEntity politicaCalificacion;
    }

    @Data
    public static class CriterioEvaluacionEntity {
        private String id;
        private String nombre;
        private String descripcion;
        private Double peso;
        private String tipo;
        private Double valorMinimo;
        private Double valorMaximo;
        private Boolean obligatorio = false;
    }

    @Data
    public static class ConfiguracionRetroalimentacionEntity {
        private Boolean mostrarNota = true;
        private Boolean mostrarRespuestasCorrectas = false;
        private Boolean mostrarRetroalimentacionPorPregunta = true;
        private Boolean permitirRevision = false;
        private Integer tiempoRevisionMinutos = 30;
    }

    @Data
    public static class PoliticaCalificacionEntity {
        private Integer decimales = 2;
        private Boolean redondeoSuperior = true;
        private Boolean permiteNotaMaxima = true;
        private Double notaMaxima = 100.0;
        private Boolean permiteNotaMinima = false;
        private Double notaMinima = 0.0;
    }

    @Data
    public static class ProgresoActividadEntity {
        private Long actividadId;
        private java.util.Map<Long, ProgresoEstudianteEntity> progresoPorEstudiante;
        private LocalDateTime ultimaActualizacion;
    }

    @Data
    public static class ProgresoEstudianteEntity {
        private Long estudianteId;
        private String estado = "NO_INICIADO";
        private Integer porcentajeAvance = 0;
        private LocalDateTime fechaInicio;
        private LocalDateTime fechaCompletado;
        private LocalDateTime fechaUltimaActualizacion;
        private Long tiempoTotalEmpleado = 0L;
        private List<InteraccionActividadEntity> interacciones;
        private ResultadoActividadEntity resultadoFinal;
        private Integer intentosRealizados = 0;
    }

    @Data
    public static class InteraccionActividadEntity {
        private String id;
        private Long estudianteId;
        private String tipo;
        private String descripcion;
        private LocalDateTime timestamp;
        private java.util.Map<String, Object> datosAdicionales;
    }

    @Data
    public static class ResultadoActividadEntity {
        private Long estudianteId;
        private Long actividadId;
        private Double nota;
        private String estadoFinal;
        private java.util.Map<String, Double> notasPorCriterio;
        private String retroalimentacionGeneral;
        private Integer intentoNumero;
        private Long tiempoEmpleadoSegundos;
    }
}
