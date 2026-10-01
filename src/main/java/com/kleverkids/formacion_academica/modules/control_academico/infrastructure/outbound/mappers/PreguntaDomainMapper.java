package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.MatchingQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.MultipleChoiceMultiQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.MultipleChoiceSingleQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.NumericQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.OpenLongQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.OpenShortQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.OrderingQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Pregunta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.ScaleQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.TrueFalseQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.MatchingPair;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Option;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.OrderingItem;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.MediaEmbeddable;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.OpcionEmbeddable;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.OrderingItemEmbeddable;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.ParEmparejamientoEmbeddable;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaAbiertaCortaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaAbiertaLargaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaEmparejamientoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaEscalaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaNumericaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaOpcionMultipleEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaOpcionMultipleUnicaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaOrdenamientoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaVerdaderoFalsoEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Convierte una {@code PreguntaEntity} (y sus 9 subtipos discriminados sobre
 * la tabla {@code questions}) al modelo de dominio rico {@code Pregunta} (y
 * sus subtipos), que es lo que {@code ServicioValidacionRespuesta.validate(...)}
 * necesita para calificar.
 *
 * <p>Es un componente NUEVO y aislado, no una corrección de
 * {@code PreguntaJpaAdapter}: ese adapter ya existente tiene su propio
 * {@code findById} implementado como stub ("Implementación mínima por ahora -
 * retornar empty para evitar errores"), pre-existente a este trabajo y fuera
 * de alcance de Activities. En vez de arreglar ese stub (lo que tocaría un
 * módulo compartido fuera del encargo y arriesgaría romper otros
 * consumidores), este mapper se usa directamente desde
 * {@code ActivityAttemptService} junto con {@code PreguntaJpaRepository} (el
 * repositorio Spring Data real, que sí funciona), de forma aislada y aditiva.
 */
@Component
public class PreguntaDomainMapper {

    public Pregunta toDomain(PreguntaEntity entity) {
        Difficulty dificultad = entity.getDificultad() != null
                ? Difficulty.fromValue(entity.getDificultad())
                : Difficulty.BASIC;
        List<Media> medios = mapearMediaList(entity.getMedia());
        int puntajeMaximo = entity.getPuntajeMaximo() != null ? entity.getPuntajeMaximo() : 1;

        return switch (entity) {
            case PreguntaOpcionMultipleUnicaEntity e -> new MultipleChoiceSingleQuestion(
                    e.getId(), e.getTextoPregunta(), dificultad, puntajeMaximo, e.getTemaId(), medios,
                    e.getHint(), e.getExplanation(), e.getTags(), e.getMetadata(),
                    mapearOpciones(e.getOptions()), e.getCorrectOptionId());
            case PreguntaOpcionMultipleEntity e -> new MultipleChoiceMultiQuestion(
                    e.getId(), e.getTextoPregunta(), dificultad, puntajeMaximo, e.getTemaId(), medios,
                    e.getHint(), e.getExplanation(), e.getTags(), e.getMetadata(),
                    mapearOpciones(e.getOptions()), e.getCorrectOptionIds(), e.getMinSelections(), e.getMaxSelections());
            case PreguntaVerdaderoFalsoEntity e -> new TrueFalseQuestion(
                    e.getId(), e.getTextoPregunta(), dificultad, puntajeMaximo, e.getTemaId(), medios,
                    e.getHint(), e.getExplanation(), e.getTags(), e.getMetadata(),
                    Boolean.TRUE.equals(e.getCorrectAnswer()));
            case PreguntaAbiertaCortaEntity e -> new OpenShortQuestion(
                    e.getId(), e.getTextoPregunta(), dificultad, puntajeMaximo, e.getTemaId(), medios,
                    e.getHint(), e.getExplanation(), e.getTags(), e.getMetadata(),
                    e.getAcceptedAnswers(), Boolean.TRUE.equals(e.getCaseSensitive()), e.getMaxLength());
            case PreguntaAbiertaLargaEntity e -> new OpenLongQuestion(
                    e.getId(), e.getTextoPregunta(), dificultad, puntajeMaximo, e.getTemaId(), medios,
                    e.getHint(), e.getExplanation(), e.getTags(), e.getMetadata(),
                    null, e.getMinWords(), e.getMaxWords(), Boolean.TRUE.equals(e.getAllowAttachments()));
            case PreguntaNumericaEntity e -> new NumericQuestion(
                    e.getId(), e.getTextoPregunta(), dificultad, puntajeMaximo, e.getTemaId(), medios,
                    e.getHint(), e.getExplanation(), e.getTags(), e.getMetadata(),
                    e.getCorrectValue(), e.getTolerance(), e.getUnit(), e.getDecimalPlaces());
            case PreguntaEscalaEntity e -> new ScaleQuestion(
                    e.getId(), e.getTextoPregunta(), dificultad, puntajeMaximo, e.getTemaId(), medios,
                    e.getHint(), e.getExplanation(), e.getTags(), e.getMetadata(),
                    mapearScaleConfig(e.getScaleConfig()), e.getExpectedValue());
            case PreguntaOrdenamientoEntity e -> new OrderingQuestion(
                    e.getId(), e.getTextoPregunta(), dificultad, puntajeMaximo, e.getTemaId(), medios,
                    e.getHint(), e.getExplanation(), e.getTags(), e.getMetadata(),
                    mapearOrderingItems(e.getItems()), Boolean.TRUE.equals(e.getPartialCredit()));
            case PreguntaEmparejamientoEntity e -> new MatchingQuestion(
                    e.getId(), e.getTextoPregunta(), dificultad, puntajeMaximo, e.getTemaId(), medios,
                    e.getHint(), e.getExplanation(), e.getTags(), e.getMetadata(),
                    mapearPares(e.getPairs()), Boolean.TRUE.equals(e.getPartialCredit()));
            default -> throw new IllegalStateException(
                    "Tipo de pregunta no soportado para calificación: " + entity.getClass().getSimpleName());
        };
    }

    private List<Option> mapearOpciones(List<OpcionEmbeddable> opciones) {
        if (opciones == null) return List.of();
        return opciones.stream()
                .map(o -> Option.create(o.getId(), o.getTexto(), mapearMedia(o.getMedia()), o.esCorrecta()))
                .toList();
    }

    private List<OrderingItem> mapearOrderingItems(List<OrderingItemEmbeddable> items) {
        if (items == null) return List.of();
        return items.stream()
                .map(i -> OrderingItem.create(i.getId(), i.getText(), i.getCorrectPosition(), mapearMedia(i.getMedia())))
                .toList();
    }

    private List<MatchingPair> mapearPares(List<ParEmparejamientoEmbeddable> pares) {
        if (pares == null) return List.of();
        return pares.stream()
                .map(p -> MatchingPair.create(p.getId(), p.getItemIzquierdo(), p.getItemDerecho(),
                        mapearMedia(p.getMediaIzquierda()), mapearMedia(p.getMediaDerecha())))
                .toList();
    }

    private com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.ScaleConfig mapearScaleConfig(
            com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.ScaleConfigEmbeddable config) {
        if (config == null) return null;
        return com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.ScaleConfig.create(
                config.getMinValue(), config.getMaxValue(), config.getMinLabel(), config.getMaxLabel(), config.getLabels());
    }

    private List<Media> mapearMediaList(List<MediaEmbeddable> medios) {
        if (medios == null) return List.of();
        return medios.stream().map(this::mapearMedia).filter(java.util.Objects::nonNull).toList();
    }

    private Media mapearMedia(MediaEmbeddable media) {
        if (media == null) return null;
        return Media.create(media.getId(), media.getType(), media.getUrl(), media.getAltText());
    }
}
