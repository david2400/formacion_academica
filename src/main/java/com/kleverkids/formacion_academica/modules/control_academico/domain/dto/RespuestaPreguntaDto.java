package com.kleverkids.formacion_academica.modules.control_academico.domain.dto;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Rubrica;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public record RespuestaPreguntaDto(
    Long id,
    String textoPregunta,
    String tipoPregunta,
    String dificultad,
    int puntajeMaximo,
    Long temaId,
    List<MediaDto> medios,
    String pista,
    String explicacion,
    List<String> etiquetas,
    Map<String, Object> metadatos,
    Instant createdAt,
    Instant updatedAt,
    Integer version,
    // Campos específicos por tipo
    List<OptionDto> opciones,
    Long idOpcionCorrecta,
    List<Long> idsOpcionesCorrectas,
    Integer minSelecciones,
    Integer maxSelecciones,
    Boolean respuestaCorrecta,
    List<String> respuestasAceptadas,
    Boolean sensibleMayusculas,
    Integer longitudMaxima,
    Rubrica rubrica,
    Integer minPalabras,
    Integer maxPalabras,
    Boolean permitirAdjuntos,
    BigDecimal valorCorrecto,
    BigDecimal tolerancia,
    String unidad,
    Integer lugaresDecimales,
    ScaleConfigDto configuracionEscala,
    Integer valorEsperado,
    List<OrderingItemDto> items,
    Boolean creditoParcial,
    List<MatchingPairDto> pares
) {

    private static Rubrica mapRubrica(RubricaDto dto) {
        if (dto == null) {
            return null;
        }
        if (dto.criterios() == null || dto.criterios().isEmpty()) {
            return Rubrica.crear(List.of());
        }
        List<Rubrica.CriterioRubrica> criterios = dto.criterios().stream()
                .map(c -> new Rubrica.CriterioRubrica(
                        c.nombre(),
                        c.descripcion(),
                        c.puntajeMaximo(),
                        c.niveles() == null
                                ? List.of()
                                : c.niveles().stream()
                                        .map(n -> new Rubrica.NivelRubrica(n.nombre(), n.descripcion(), n.puntaje()))
                                        .toList()))
                .toList();
        return Rubrica.crear(criterios);
    }

    public static class RespuestaPreguntaBuilder {
        // Builder implementation
        private Long id;
        private String textoPregunta;
        private String tipoPregunta;
        private String dificultad;
        private int puntajeMaximo;
        private Long temaId;
        private List<MediaDto> medios;
        private String pista;
        private String explicacion;
        private List<String> etiquetas;
        private Map<String, Object> metadatos;
        private Instant createdAt;
        private Instant updatedAt;
        private Integer version;
        private List<OptionDto> opciones;
        private Long idOpcionCorrecta;
        private List<Long> idsOpcionesCorrectas;
        private Integer minSelecciones;
        private Integer maxSelecciones;
        private Boolean respuestaCorrecta;
        private List<String> respuestasAceptadas;
        private Boolean sensibleMayusculas;
        private Integer longitudMaxima;
        private RubricaDto rubrica;
        private Integer minPalabras;
        private Integer maxPalabras;
        private Boolean permitirAdjuntos;
        private BigDecimal valorCorrecto;
        private BigDecimal tolerancia;
        private String unidad;
        private Integer lugaresDecimales;
        private ScaleConfigDto configuracionEscala;
        private Integer valorEsperado;
        private List<OrderingItemDto> items;
        private Boolean creditoParcial;
        private List<MatchingPairDto> pares;
        
        public RespuestaPreguntaBuilder id(Long id) { this.id = id; return this; }
        public RespuestaPreguntaBuilder textoPregunta(String textoPregunta) { this.textoPregunta = textoPregunta; return this; }
        public RespuestaPreguntaBuilder tipoPregunta(String tipoPregunta) { this.tipoPregunta = tipoPregunta; return this; }
        public RespuestaPreguntaBuilder dificultad(String dificultad) { this.dificultad = dificultad; return this; }
        public RespuestaPreguntaBuilder puntajeMaximo(int puntajeMaximo) { this.puntajeMaximo = puntajeMaximo; return this; }
        public RespuestaPreguntaBuilder temaId(Long temaId) { this.temaId = temaId; return this; }
        public RespuestaPreguntaBuilder medios(List<MediaDto> medios) { this.medios = medios; return this; }
        public RespuestaPreguntaBuilder pista(String pista) { this.pista = pista; return this; }
        public RespuestaPreguntaBuilder explicacion(String explicacion) { this.explicacion = explicacion; return this; }
        public RespuestaPreguntaBuilder etiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; return this; }
        public RespuestaPreguntaBuilder metadatos(Map<String, Object> metadatos) { this.metadatos = metadatos; return this; }
        public RespuestaPreguntaBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public RespuestaPreguntaBuilder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }
        public RespuestaPreguntaBuilder version(Integer version) { this.version = version; return this; }
        public RespuestaPreguntaBuilder opciones(List<OptionDto> opciones) { this.opciones = opciones; return this; }
        public RespuestaPreguntaBuilder idOpcionCorrecta(Long idOpcionCorrecta) { this.idOpcionCorrecta = idOpcionCorrecta; return this; }
        public RespuestaPreguntaBuilder idsOpcionesCorrectas(List<Long> idsOpcionesCorrectas) { this.idsOpcionesCorrectas = idsOpcionesCorrectas; return this; }
        public RespuestaPreguntaBuilder minSelecciones(Integer minSelecciones) { this.minSelecciones = minSelecciones; return this; }
        public RespuestaPreguntaBuilder maxSelecciones(Integer maxSelecciones) { this.maxSelecciones = maxSelecciones; return this; }
        public RespuestaPreguntaBuilder respuestaCorrecta(Boolean respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; return this; }
        public RespuestaPreguntaBuilder respuestasAceptadas(List<String> respuestasAceptadas) { this.respuestasAceptadas = respuestasAceptadas; return this; }
        public RespuestaPreguntaBuilder sensibleMayusculas(Boolean sensibleMayusculas) { this.sensibleMayusculas = sensibleMayusculas; return this; }
        public RespuestaPreguntaBuilder longitudMaxima(Integer longitudMaxima) { this.longitudMaxima = longitudMaxima; return this; }
        public RespuestaPreguntaBuilder rubrica(RubricaDto rubrica) { this.rubrica = rubrica; return this; }
        public RespuestaPreguntaBuilder minPalabras(Integer minPalabras) { this.minPalabras = minPalabras; return this; }
        public RespuestaPreguntaBuilder maxPalabras(Integer maxPalabras) { this.maxPalabras = maxPalabras; return this; }
        public RespuestaPreguntaBuilder permitirAdjuntos(Boolean permitirAdjuntos) { this.permitirAdjuntos = permitirAdjuntos; return this; }
        public RespuestaPreguntaBuilder valorCorrecto(BigDecimal valorCorrecto) { this.valorCorrecto = valorCorrecto; return this; }
        public RespuestaPreguntaBuilder tolerancia(BigDecimal tolerancia) { this.tolerancia = tolerancia; return this; }
        public RespuestaPreguntaBuilder unidad(String unidad) { this.unidad = unidad; return this; }
        public RespuestaPreguntaBuilder lugaresDecimales(Integer lugaresDecimales) { this.lugaresDecimales = lugaresDecimales; return this; }
        public RespuestaPreguntaBuilder configuracionEscala(ScaleConfigDto configuracionEscala) { this.configuracionEscala = configuracionEscala; return this; }
        public RespuestaPreguntaBuilder valorEsperado(Integer valorEsperado) { this.valorEsperado = valorEsperado; return this; }
        public RespuestaPreguntaBuilder items(List<OrderingItemDto> items) { this.items = items; return this; }
        public RespuestaPreguntaBuilder creditoParcial(Boolean creditoParcial) { this.creditoParcial = creditoParcial; return this; }
        public RespuestaPreguntaBuilder pares(List<MatchingPairDto> pares) { this.pares = pares; return this; }
        
        public RespuestaPreguntaDto build() {
            return new RespuestaPreguntaDto(
                id, textoPregunta, tipoPregunta, dificultad, puntajeMaximo, temaId,
                medios, pista, explicacion, etiquetas, metadatos, createdAt, updatedAt, version,
                opciones, idOpcionCorrecta, idsOpcionesCorrectas, minSelecciones, maxSelecciones,
                respuestaCorrecta, respuestasAceptadas, sensibleMayusculas, longitudMaxima,
                mapRubrica(rubrica), minPalabras, maxPalabras, permitirAdjuntos, valorCorrecto, tolerancia,
                unidad, lugaresDecimales, configuracionEscala, valorEsperado, items, creditoParcial, pares
            );
        }
    }
}
