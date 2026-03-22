package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class EvaluacionConfiguracion {
    
    private final TipoEvaluacion tipo;
    private final Boolean automatica;
    private final BigDecimal notaAprobacion;
    private final BigDecimal pesoTotal;
    private final List<CriterioEvaluacion> criterios;
    private final ConfiguracionRetroalimentacion retroalimentacion;
    private final PoliticaCalificacion politicaCalificacion;
    
    private EvaluacionConfiguracion(Builder builder) {
        this.tipo = builder.tipo;
        this.automatica = builder.automatica;
        this.notaAprobacion = builder.notaAprobacion;
        this.pesoTotal = builder.pesoTotal;
        this.criterios = builder.criterios;
        this.retroalimentacion = builder.retroalimentacion;
        this.politicaCalificacion = builder.politicaCalificacion;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static EvaluacionConfiguracion automatica(BigDecimal notaAprobacion) {
        return builder()
            .tipo(TipoEvaluacion.CUANTITATIVA)
            .automatica(true)
            .notaAprobacion(notaAprobacion)
            .pesoTotal(new BigDecimal("100"))
            .politicaCalificacion(PoliticaCalificacion.estandar())
            .build();
    }
    
    public static EvaluacionConfiguracion manual() {
        return builder()
            .tipo(TipoEvaluacion.CUALITATIVA)
            .automatica(false)
            .politicaCalificacion(PoliticaCalificacion.cualitativa())
            .build();
    }
    
    public boolean esAutomatica() {
        return automatica;
    }
    
    public boolean evaluar(ResultadoActividad resultado) {
        return switch (tipo) {
            case CUANTITATIVA -> evaluarCuantitativa(resultado);
            case CUALITATIVA -> evaluarCualitativa(resultado);
            case MIXTA -> evaluarMixta(resultado);
        };
    }
    
    private boolean evaluarCuantitativa(ResultadoActividad resultado) {
        if (resultado.nota() == null) return false;
        return resultado.nota().compareTo(notaAprobacion) >= 0;
    }
    
    private boolean evaluarCualitativa(ResultadoActividad resultado) {
        return resultado.estadoFinal() == EstadoResultado.APROBADO;
    }
    
    private boolean evaluarMixta(ResultadoActividad resultado) {
        return evaluarCuantitativa(resultado) && evaluarCualitativa(resultado);
    }
    
    public BigDecimal calcularNotaFinal(Map<String, BigDecimal> notasPorCriterio) {
        if (tipo == TipoEvaluacion.CUALITATIVA) {
            return null;
        }
        
        BigDecimal notaTotal = BigDecimal.ZERO;
        for (CriterioEvaluacion criterio : criterios) {
            BigDecimal notaCriterio = notasPorCriterio.get(criterio.id());
            if (notaCriterio != null) {
                BigDecimal ponderada = notaCriterio
                    .multiply(criterio.peso())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                notaTotal = notaTotal.add(ponderada);
            }
        }
        return notaTotal;
    }
    
    // Getters
    public TipoEvaluacion getTipo() { return tipo; }
    public Boolean getAutomatica() { return automatica; }
    public BigDecimal getNotaAprobacion() { return notaAprobacion; }
    public BigDecimal getPesoTotal() { return pesoTotal; }
    public List<CriterioEvaluacion> getCriterios() { return criterios; }
    public ConfiguracionRetroalimentacion getRetroalimentacion() { return retroalimentacion; }
    public PoliticaCalificacion getPoliticaCalificacion() { return politicaCalificacion; }
    
    public static class Builder {
        private TipoEvaluacion tipo = TipoEvaluacion.CUANTITATIVA;
        private Boolean automatica = true;
        private BigDecimal notaAprobacion = new BigDecimal("70");
        private BigDecimal pesoTotal = new BigDecimal("100");
        private List<CriterioEvaluacion> criterios = List.of();
        private ConfiguracionRetroalimentacion retroalimentacion;
        private PoliticaCalificacion politicaCalificacion;
        
        public Builder tipo(TipoEvaluacion tipo) { this.tipo = tipo; return this; }
        public Builder automatica(Boolean automatica) { this.automatica = automatica; return this; }
        public Builder notaAprobacion(BigDecimal notaAprobacion) { this.notaAprobacion = notaAprobacion; return this; }
        public Builder pesoTotal(BigDecimal pesoTotal) { this.pesoTotal = pesoTotal; return this; }
        public Builder criterios(List<CriterioEvaluacion> criterios) { this.criterios = criterios; return this; }
        public Builder retroalimentacion(ConfiguracionRetroalimentacion retroalimentacion) { this.retroalimentacion = retroalimentacion; return this; }
        public Builder politicaCalificacion(PoliticaCalificacion politicaCalificacion) { this.politicaCalificacion = politicaCalificacion; return this; }
        
        public EvaluacionConfiguracion build() {
            return new EvaluacionConfiguracion(this);
        }
    }
    
    public enum TipoEvaluacion {
        CUANTITATIVA, CUALITATIVA, MIXTA
    }
    
    public static record CriterioEvaluacion(
        String id,
        String nombre,
        String descripcion,
        BigDecimal peso,
        String tipo, // "NUMERICA", "CATEGORICA", "BOOLEANA"
        BigDecimal valorMinimo,
        BigDecimal valorMaximo,
        Boolean obligatorio
    ) {}
    
    public static record ConfiguracionRetroalimentacion(
        Boolean mostrarNota,
        Boolean mostrarRespuestasCorrectas,
        Boolean mostrarRetroalimentacionPorPregunta,
        Boolean permitirRevision,
        Integer tiempoRevisionMinutos
    ) {}
    
    public static record PoliticaCalificacion(
        Integer decimales,
        Boolean redondeoSuperior,
        Boolean permiteNotaMaxima,
        BigDecimal notaMaxima,
        Boolean permiteNotaMinima,
        BigDecimal notaMinima
    ) {
        public static PoliticaCalificacion estandar() {
            return new PoliticaCalificacion(
                2, true, true, new BigDecimal("100"), false, BigDecimal.ZERO
            );
        }
        
        public static PoliticaCalificacion cualitativa() {
            return new PoliticaCalificacion(
                0, false, false, null, false, null
            );
        }
    }
    
    public static record ResultadoActividad(
        Long estudianteId,
        Long actividadId,
        BigDecimal nota,
        EstadoResultado estadoFinal,
        Map<String, BigDecimal> notasPorCriterio,
        String retroalimentacionGeneral,
        Integer intentoNumero,
        Long tiempoEmpleadoSegundos
    ) {}
    
    public enum EstadoResultado {
        PENDIENTE, EN_PROGRESO, COMPLETADO, APROBADO, RECHAZADO, EXPIRADO
    }
}
