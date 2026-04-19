# 📚 Guía de API de Preguntas

## 🎯 Descripción General

Sistema completo de gestión de preguntas que soporta **9 tipos diferentes** de preguntas con un diseño unificado usando **Factory Pattern** y **Sealed Interfaces**.

## 🔧 Tipos de Preguntas Soportados

| Tipo | Código | Descripción |
|------|--------|-------------|
| Opción Múltiple Única | `multiple_choice_single` | Una sola respuesta correcta |
| Opción Múltiple Múltiple | `multiple_choice_multiple` | Múltiples respuestas correctas |
| Verdadero/Falso | `true_false` | Respuesta booleana |
| Respuesta Corta | `short_answer` | Texto breve |
| Ensayo | `essay` | Texto largo con rúbrica |
| Numérica | `numeric` | Respuesta numérica con tolerancia |
| Escala | `scale` | Valoración en escala |
| Ordenamiento | `ordering` | Ordenar elementos |
| Emparejamiento | `matching` | Emparejar elementos |

## 📡 Endpoints REST

### 1. Crear Pregunta
```http
POST /control-academico/preguntas
Content-Type: application/json

{
  "questionType": "multiple_choice_single",
  "questionText": "¿Cuál es la capital de Francia?",
  "difficulty": "facil",
  "maxScore": 10,
  "options": [
    {"id": "1", "text": "París", "isCorrect": true},
    {"id": "2", "text": "Londres", "isCorrect": false}
  ]
}
```

**Respuesta:** `201 Created`

### 2. Actualizar Pregunta
```http
PUT /control-academico/preguntas/{preguntaId}
Content-Type: application/json

{
  "questionType": "multiple_choice_single",
  "questionText": "¿Cuál es la capital de Francia? (Actualizada)",
  "difficulty": "medio",
  "maxScore": 15,
  "options": [...]
}
```

**Respuesta:** `200 OK`

**⚠️ IMPORTANTE:** No se puede cambiar el tipo de pregunta en actualizaciones.

### 3. Consultar Pregunta
```http
GET /control-academico/preguntas/{preguntaId}
```

**Respuesta:** `200 OK`

### 4. Buscar Preguntas
```http
GET /control-academico/preguntas?temaId=1&dificultad=medio
```

**Parámetros opcionales:**
- `temaId`: Filtrar por tema
- `dificultad`: Filtrar por dificultad (facil, medio, dificil)
- `tipoPregunta`: Filtrar por tipo

### 5. Eliminar Pregunta
```http
DELETE /control-academico/preguntas/{preguntaId}
```

**Respuesta:** `204 No Content`

### 6. Clonar Pregunta
```http
POST /control-academico/preguntas/{preguntaId}/clonar
```

**Respuesta:** `201 Created`

### 7. Cambiar Dificultad
```http
PATCH /control-academico/preguntas/{preguntaId}/dificultad?dificultad=dificil
```

### 8. Asignar Tema
```http
PATCH /control-academico/preguntas/{preguntaId}/tema?temaId=5
```

## 📝 Ejemplos por Tipo de Pregunta

### Opción Múltiple Única
```json
{
  "questionType": "multiple_choice_single",
  "questionText": "¿Cuál es 2+2?",
  "difficulty": "facil",
  "maxScore": 5,
  "options": [
    {"id": "1", "text": "3", "isCorrect": false},
    {"id": "2", "text": "4", "isCorrect": true},
    {"id": "3", "text": "5", "isCorrect": false}
  ],
  "correctOptionId": "2"
}
```

### Verdadero/Falso
```json
{
  "questionType": "true_false",
  "questionText": "La Tierra es plana",
  "difficulty": "facil",
  "maxScore": 5,
  "correctAnswer": false
}
```

### Respuesta Corta
```json
{
  "questionType": "short_answer",
  "questionText": "¿Quién escribió Don Quijote?",
  "difficulty": "medio",
  "maxScore": 10,
  "expectedAnswer": "Miguel de Cervantes",
  "caseSensitive": false
}
```

### Ensayo con Rúbrica
```json
{
  "questionType": "essay",
  "questionText": "Explica la teoría de la relatividad",
  "difficulty": "dificil",
  "maxScore": 50,
  "rubric": {
    "criteria": [
      {
        "name": "Claridad",
        "description": "Claridad en la explicación",
        "levels": [
          {"name": "Excelente", "points": 10, "description": "Muy claro"},
          {"name": "Bueno", "points": 7, "description": "Claro"}
        ]
      }
    ]
  }
}
```

### Numérica
```json
{
  "questionType": "numeric",
  "questionText": "¿Cuánto es π (pi)?",
  "difficulty": "medio",
  "maxScore": 10,
  "correctAnswer": 3.14159,
  "tolerance": 0.01
}
```

### Escala
```json
{
  "questionType": "scale",
  "questionText": "¿Qué tan satisfecho estás?",
  "difficulty": "facil",
  "maxScore": 5,
  "scaleConfig": {
    "minValue": 1,
    "maxValue": 5,
    "minLabel": "Muy insatisfecho",
    "maxLabel": "Muy satisfecho"
  }
}
```

### Ordenamiento
```json
{
  "questionType": "ordering",
  "questionText": "Ordena los planetas por distancia al Sol",
  "difficulty": "medio",
  "maxScore": 15,
  "items": [
    {"id": "1", "text": "Mercurio", "correctPosition": 1},
    {"id": "2", "text": "Venus", "correctPosition": 2},
    {"id": "3", "text": "Tierra", "correctPosition": 3}
  ]
}
```

### Emparejamiento
```json
{
  "questionType": "matching",
  "questionText": "Empareja países con capitales",
  "difficulty": "medio",
  "maxScore": 20,
  "pairs": [
    {"id": "1", "leftItem": "Francia", "rightItem": "París"},
    {"id": "2", "leftItem": "España", "rightItem": "Madrid"}
  ]
}
```

## 🏗️ Arquitectura

### Factory Pattern
Cada tipo de pregunta tiene su propio factory:
- `OpcionMultipleUnicaFactory`
- `VerdaderoFalsoFactory`
- `AbiertaCortaFactory`
- etc.

### Sealed Interface
`CreateQuestionCommand` es una sealed interface con implementaciones específicas para cada tipo.

### Transacciones
- Operaciones de escritura: `@Transactional`
- Operaciones de lectura: `@Transactional(readOnly = true)`

## 🛡️ Manejo de Errores

### Respuestas de Error (RFC 7807)
```json
{
  "type": "https://api.kleverkids.com/errors/pregunta-not-found",
  "title": "Pregunta No Encontrada",
  "status": 404,
  "detail": "Pregunta con ID 123 no encontrada",
  "timestamp": "2026-04-18T23:00:00Z"
}
```

### Códigos de Error
- `404`: Pregunta no encontrada
- `400`: Tipo de pregunta no soportado o inmutable
- `400`: Error de validación
- `500`: Error interno del servidor

## ✅ Validaciones

1. **Tipo de pregunta obligatorio**
2. **Texto de pregunta obligatorio**
3. **No cambio de tipo en actualizaciones**
4. **Validaciones específicas por tipo** (opciones, respuestas correctas, etc.)

## 🚀 Características Avanzadas

- ✅ **Logging estructurado** en todos los niveles
- ✅ **Validaciones de negocio** robustas
- ✅ **Manejo global de excepciones**
- ✅ **Documentación Swagger completa**
- ✅ **Búsqueda con filtros múltiples**
- ✅ **Clonación de preguntas**
- ✅ **Operaciones PATCH para actualizaciones parciales**

## 📊 Swagger UI

Accede a la documentación interactiva en:
```
http://localhost:8080/swagger-ui.html
```

## 🔐 Seguridad

- Validación de entrada con `@Valid`
- Transacciones ACID
- Prevención de SQL injection (JPA)
- Manejo seguro de errores sin exponer detalles internos
