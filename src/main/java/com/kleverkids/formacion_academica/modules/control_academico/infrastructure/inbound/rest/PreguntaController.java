package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.PreguntaService;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CriterioBusquedaPregunta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaDto;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Preguntas", description = "API para gestión de preguntas del sistema académico")
@Description(value = "Gestiona las preguntas")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/preguntas")
public class PreguntaController {
    
    private final PreguntaService preguntaService;

    @PostMapping
    @Operation(
        summary = "Crear pregunta",
        description = "Crea una nueva pregunta de cualquier tipo. Selecciona un ejemplo según el tipo que necesites.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la pregunta a crear",
            required = true,
            content = @Content(
            examples = {
                @ExampleObject(
                    name = "Opción Múltiple Única",
                    summary = "Pregunta de opción múltiple con una sola respuesta correcta",
                    value = """
                        {
                          "questionType": "multiple_choice_single",
                          "questionText": "¿Cuál es la capital de Francia?",
                          "difficulty": "facil",
                          "maxScore": 10,
                          "thematicId": 1,
                          "hint": "Es conocida como la ciudad de la luz",
                          "explanation": "París es la capital y ciudad más poblada de Francia",
                          "tags": ["geografía", "europa", "capitales"],
                          "options": [
                            {"id": "1", "text": "Londres", "isCorrect": false},
                            {"id": "2", "text": "París", "isCorrect": true},
                            {"id": "3", "text": "Berlín", "isCorrect": false},
                            {"id": "4", "text": "Madrid", "isCorrect": false}
                          ],
                          "correctOptionId": "2"
                        }
                        """
                ),
                @ExampleObject(
                    name = "Opción Múltiple Múltiple",
                    summary = "Pregunta con múltiples respuestas correctas",
                    value = """
                        {
                          "questionType": "multiple_choice_multi",
                          "questionText": "¿Cuáles son lenguajes de programación?",
                          "difficulty": "medio",
                          "maxScore": 15,
                          "thematicId": 2,
                          "hint": "Piensa en lenguajes populares",
                          "tags": ["programación", "lenguajes"],
                          "options": [
                            {"id": "1", "text": "Java", "isCorrect": true},
                            {"id": "2", "text": "HTML", "isCorrect": false},
                            {"id": "3", "text": "Python", "isCorrect": true},
                            {"id": "4", "text": "CSS", "isCorrect": false},
                            {"id": "5", "text": "JavaScript", "isCorrect": true}
                          ],
                          "correctOptionIds": ["1", "3", "5"]
                        }
                        """
                ),
                @ExampleObject(
                    name = "Verdadero/Falso",
                    summary = "Pregunta de verdadero o falso",
                    value = """
                        {
                          "questionType": "true_false",
                          "questionText": "La Tierra es el tercer planeta del sistema solar",
                          "difficulty": "facil",
                          "maxScore": 5,
                          "thematicId": 3,
                          "hint": "Cuenta desde el Sol",
                          "explanation": "La Tierra es el tercer planeta en orden de distancia al Sol",
                          "tags": ["astronomía", "sistema solar"],
                          "correctAnswer": true
                        }
                        """
                ),
                @ExampleObject(
                    name = "Respuesta Corta",
                    summary = "Pregunta de respuesta abierta corta",
                    value = """
                        {
                          "questionType": "short_answer",
                          "questionText": "¿Quién escribió 'Don Quijote de la Mancha'?",
                          "difficulty": "medio",
                          "maxScore": 10,
                          "thematicId": 4,
                          "hint": "Escritor español del Siglo de Oro",
                          "tags": ["literatura", "española"],
                          "expectedAnswer": "Miguel de Cervantes",
                          "caseSensitive": false,
                          "acceptableAnswers": ["Cervantes", "Miguel de Cervantes Saavedra"]
                        }
                        """
                ),
                @ExampleObject(
                    name = "Ensayo",
                    summary = "Pregunta de respuesta larga con rúbrica de evaluación",
                    value = """
                        {
                          "questionType": "open_long",
                          "questionText": "Explica la teoría de la relatividad de Einstein y su impacto en la física moderna",
                          "difficulty": "dificil",
                          "maxScore": 50,
                          "thematicId": 5,
                          "hint": "Considera tanto la relatividad especial como la general",
                          "tags": ["física", "einstein", "relatividad"],
                          "minWords": 200,
                          "maxWords": 500,
                          "rubric": {
                            "criteria": [
                              {
                                "name": "Comprensión del concepto",
                                "description": "Demuestra entendimiento de la teoría",
                                "weight": 40,
                                "levels": [
                                  {"name": "Excelente", "points": 20, "description": "Explicación completa y precisa"},
                                  {"name": "Bueno", "points": 15, "description": "Explicación correcta con detalles menores faltantes"},
                                  {"name": "Regular", "points": 10, "description": "Explicación básica"},
                                  {"name": "Insuficiente", "points": 5, "description": "Explicación incorrecta o incompleta"}
                                ]
                              },
                              {
                                "name": "Claridad y organización",
                                "description": "Estructura y coherencia del texto",
                                "weight": 30,
                                "levels": [
                                  {"name": "Excelente", "points": 15, "description": "Muy bien estructurado"},
                                  {"name": "Bueno", "points": 10, "description": "Bien organizado"},
                                  {"name": "Regular", "points": 5, "description": "Organización básica"}
                                ]
                              }
                            ]
                          }
                        }
                        """
                ),
                @ExampleObject(
                    name = "Numérica",
                    summary = "Pregunta con respuesta numérica y tolerancia",
                    value = """
                        {
                          "questionType": "numeric",
                          "questionText": "¿Cuál es el valor de π (pi) con 5 decimales?",
                          "difficulty": "medio",
                          "maxScore": 10,
                          "thematicId": 6,
                          "hint": "Empieza con 3.14159...",
                          "tags": ["matemáticas", "constantes"],
                          "correctAnswer": 3.14159,
                          "tolerance": 0.00001,
                          "unit": "sin unidad"
                        }
                        """
                ),
                @ExampleObject(
                    name = "Escala",
                    summary = "Pregunta de valoración en escala",
                    value = """
                        {
                          "questionType": "scale",
                          "questionText": "¿Qué tan satisfecho estás con el curso?",
                          "difficulty": "facil",
                          "maxScore": 5,
                          "thematicId": 7,
                          "tags": ["evaluación", "satisfacción"],
                          "scaleConfig": {
                            "minValue": 1,
                            "maxValue": 5,
                            "minLabel": "Muy insatisfecho",
                            "maxLabel": "Muy satisfecho",
                            "step": 1
                          }
                        }
                        """
                ),
                @ExampleObject(
                    name = "Ordenamiento",
                    summary = "Pregunta de ordenar elementos en secuencia correcta",
                    value = """
                        {
                          "questionType": "ordering",
                          "questionText": "Ordena los siguientes planetas por su distancia al Sol (del más cercano al más lejano)",
                          "difficulty": "medio",
                          "maxScore": 20,
                          "thematicId": 8,
                          "hint": "Mercurio es el más cercano",
                          "tags": ["astronomía", "planetas"],
                          "items": [
                            {"id": "1", "text": "Mercurio", "correctPosition": 1},
                            {"id": "2", "text": "Venus", "correctPosition": 2},
                            {"id": "3", "text": "Tierra", "correctPosition": 3},
                            {"id": "4", "text": "Marte", "correctPosition": 4},
                            {"id": "5", "text": "Júpiter", "correctPosition": 5}
                          ]
                        }
                        """
                ),
                @ExampleObject(
                    name = "Emparejamiento",
                    summary = "Pregunta de emparejar elementos relacionados",
                    value = """
                        {
                          "questionType": "matching",
                          "questionText": "Empareja cada país con su capital",
                          "difficulty": "medio",
                          "maxScore": 25,
                          "thematicId": 9,
                          "hint": "Piensa en las capitales europeas",
                          "tags": ["geografía", "capitales", "europa"],
                          "pairs": [
                            {"id": "1", "leftItem": "Francia", "rightItem": "París"},
                            {"id": "2", "leftItem": "España", "rightItem": "Madrid"},
                            {"id": "3", "leftItem": "Italia", "rightItem": "Roma"},
                            {"id": "4", "leftItem": "Alemania", "rightItem": "Berlín"},
                            {"id": "5", "leftItem": "Portugal", "rightItem": "Lisboa"}
                          ]
                        }
                        """
                )
            }
        )
    ))
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Pregunta creada exitosamente",
            content = @Content(schema = @Schema(implementation = PreguntaDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos o JSON mal formado"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tema no encontrado"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<PreguntaDto> crear(@Valid @org.springframework.web.bind.annotation.RequestBody CreateQuestionCommand command) {
        var resultado = preguntaService.guardar(null, command);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
    
    @PutMapping("/{preguntaId}")
    @Operation(
        summary = "Actualizar pregunta",
        description = """
            Actualiza una pregunta existente de cualquier tipo.
            
            IMPORTANTE:
            - No se permite cambiar el tipo de pregunta
            - El tipo en el comando debe coincidir con el tipo actual de la pregunta
            - Todos los campos se actualizan con los valores proporcionados
            
            Tipos soportados: multiple_choice_single, multiple_choice_multi, true_false, 
            open_short, open_long, numeric, scale, ordering, matching
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Pregunta actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = PreguntaDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos o intento de cambiar el tipo de pregunta"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pregunta o tema no encontrado"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<PreguntaDto> actualizar(
            @PathVariable Long preguntaId,
            @Valid @org.springframework.web.bind.annotation.RequestBody CreateQuestionCommand command) {
        
        log.info("Actualizando pregunta ID: {} con tipo: {}", preguntaId, command.questionType());
        var resultado = preguntaService.guardar(preguntaId, command);
        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping
    @Operation(
        summary = "Buscar preguntas",
        description = """
            Busca preguntas aplicando filtros opcionales:
            - temaId: Filtra por tema
            - dificultad: Filtra por nivel de dificultad
            - tipoPregunta: Filtra por tipo de pregunta
            Si no se proporciona ningún filtro, devuelve todas las preguntas
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de preguntas encontradas (puede estar vacía)"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<List<PreguntaDto>> buscar(
            @RequestParam(required = false) Long temaId,
            @RequestParam(required = false) String dificultad,
            @RequestParam(required = false) String tipoPregunta) {
        
        CriterioBusquedaPregunta criterios = new CriterioBusquedaPregunta(
            tipoPregunta, dificultad, temaId, null, null, false
        );
        
        return ResponseEntity.ok(preguntaService.buscar(criterios));
    }
    
    @GetMapping("/{preguntaId}")
    @Operation(
        summary = "Obtener pregunta por ID",
        description = "Recupera una pregunta completa con todos sus datos específicos según el tipo"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Pregunta encontrada",
            content = @Content(schema = @Schema(implementation = PreguntaDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pregunta no encontrada"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<PreguntaDto> consultar(@PathVariable Long preguntaId) {
        return ResponseEntity.ok(preguntaService.consultarPorId(preguntaId));
    }
    
    @DeleteMapping("/{preguntaId}")
    @Operation(
        summary = "Eliminar pregunta",
        description = "Elimina permanentemente una pregunta del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Pregunta eliminada exitosamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pregunta no encontrada"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long preguntaId) {
        preguntaService.eliminar(preguntaId);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{preguntaId}/clonar")
    @Operation(
        summary = "Clonar pregunta",
        description = "Crea una copia exacta de una pregunta existente con un nuevo ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Pregunta clonada exitosamente",
            content = @Content(schema = @Schema(implementation = PreguntaDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pregunta original no encontrada"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<PreguntaDto> clonar(@PathVariable Long preguntaId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaService.clonar(preguntaId));
    }
    
    @PatchMapping("/{preguntaId}/dificultad")
    @Operation(
        summary = "Cambiar dificultad",
        description = "Modifica el nivel de dificultad de una pregunta (facil, medio, dificil)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Dificultad actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = PreguntaDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Nivel de dificultad inválido"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pregunta no encontrada"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<PreguntaDto> cambiarDificultad(
            @PathVariable Long preguntaId,
            @RequestParam String dificultad) {
        return ResponseEntity.ok(preguntaService.cambiarDificultad(preguntaId, dificultad));
    }
    
    @PatchMapping("/{preguntaId}/tema")
    @Operation(
        summary = "Asignar tema",
        description = "Asocia una pregunta a un tema o temática específica"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Tema asignado exitosamente",
            content = @Content(schema = @Schema(implementation = PreguntaDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pregunta o tema no encontrado"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<PreguntaDto> asignarTema(
            @PathVariable Long preguntaId,
            @RequestParam Long temaId) {
        return ResponseEntity.ok(preguntaService.asignarTema(preguntaId, temaId));
    }
}
