# 📋 Ejemplos JSON para Pruebas de Preguntas

## 🎯 Cómo usar estos ejemplos

1. Abre Swagger UI: `http://localhost:8080/swagger-ui.html`
2. Ve al endpoint `POST /control-academico/preguntas`
3. Copia y pega cualquiera de estos JSON en el cuerpo de la petición
4. Haz clic en "Execute"

---

## 1️⃣ Opción Múltiple Única

```json
{
  "question_type": "multiple_choice_single",
  "question_text": "¿Cuál es la capital de Francia?",
  "difficulty": "facil",
  "max_score": 10,
  "theme_id": 1,
  "hint": "Es conocida como la ciudad de la luz",
  "explanation": "París es la capital y ciudad más poblada de Francia",
  "tags": ["geografía", "europa", "capitales"],
  "options": [
    {"id": "1", "text": "Londres", "is_correct": false},
    {"id": "2", "text": "París", "is_correct": true},
    {"id": "3", "text": "Berlín", "is_correct": false},
    {"id": "4", "text": "Madrid", "is_correct": false}
  ],
  "correct_option_id": "2"
}
```

---

## 2️⃣ Opción Múltiple Múltiple

```json
{
  "question_type": "multiple_choice_multi",
  "question_text": "¿Cuáles son lenguajes de programación?",
  "difficulty": "medio",
  "max_score": 15,
  "theme_id": 2,
  "hint": "Piensa en lenguajes populares",
  "tags": ["programación", "lenguajes"],
  "options": [
    {"id": "1", "text": "Java", "is_correct": true},
    {"id": "2", "text": "HTML", "is_correct": false},
    {"id": "3", "text": "Python", "is_correct": true},
    {"id": "4", "text": "CSS", "is_correct": false},
    {"id": "5", "text": "JavaScript", "is_correct": true}
  ],
  "correct_option_ids": ["1", "3", "5"]
}
```

---

## 3️⃣ Verdadero/Falso

```json
{
  "question_type": "true_false",
  "question_text": "La Tierra es el tercer planeta del sistema solar",
  "difficulty": "facil",
  "max_score": 5,
  "theme_id": 3,
  "hint": "Cuenta desde el Sol",
  "explanation": "La Tierra es el tercer planeta en orden de distancia al Sol",
  "tags": ["astronomía", "sistema solar"],
  "correct_answer": true
}
```

---

## 4️⃣ Respuesta Corta

```json
{
  "question_type": "open_short",
  "question_text": "¿Quién escribió 'Don Quijote de la Mancha'?",
  "difficulty": "medio",
  "max_score": 10,
  "theme_id": 4,
  "hint": "Escritor español del Siglo de Oro",
  "tags": ["literatura", "española"],
  "expected_answer": "Miguel de Cervantes",
  "case_sensitive": false,
  "acceptable_answers": ["Cervantes", "Miguel de Cervantes Saavedra"]
}
```

---

## 5️⃣ Ensayo con Rúbrica

```json
{
  "question_type": "open_long",
  "question_text": "Explica la teoría de la relatividad de Einstein y su impacto en la física moderna",
  "difficulty": "dificil",
  "max_score": 50,
  "theme_id": 5,
  "hint": "Considera tanto la relatividad especial como la general",
  "tags": ["física", "einstein", "relatividad"],
  "min_words": 200,
  "max_words": 500,
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
```

---

## 6️⃣ Numérica

```json
{
  "question_type": "numeric",
  "question_text": "¿Cuál es el valor de π (pi) con 5 decimales?",
  "difficulty": "medio",
  "max_score": 10,
  "theme_id": 6,
  "hint": "Empieza con 3.14159...",
  "tags": ["matemáticas", "constantes"],
  "correct_answer": 3.14159,
  "tolerance": 0.00001,
  "unit": "sin unidad"
}
```

---

## 7️⃣ Escala de Valoración

```json
{
  "question_type": "scale",
  "question_text": "¿Qué tan satisfecho estás con el curso?",
  "difficulty": "facil",
  "max_score": 5,
  "theme_id": 7,
  "tags": ["evaluación", "satisfacción"],
  "scale_config": {
    "min_value": 1,
    "max_value": 5,
    "min_label": "Muy insatisfecho",
    "max_label": "Muy satisfecho",
    "step": 1
  }
}
```

---

## 8️⃣ Ordenamiento

```json
{
  "question_type": "ordering",
  "question_text": "Ordena los siguientes planetas por su distancia al Sol (del más cercano al más lejano)",
  "difficulty": "medio",
  "max_score": 20,
  "theme_id": 8,
  "hint": "Mercurio es el más cercano",
  "tags": ["astronomía", "planetas"],
  "items": [
    {"id": "1", "text": "Mercurio", "correct_position": 1},
    {"id": "2", "text": "Venus", "correct_position": 2},
    {"id": "3", "text": "Tierra", "correct_position": 3},
    {"id": "4", "text": "Marte", "correct_position": 4},
    {"id": "5", "text": "Júpiter", "correct_position": 5}
  ]
}
```

---

## 9️⃣ Emparejamiento

```json
{
  "question_type": "matching",
  "question_text": "Empareja cada país con su capital",
  "difficulty": "medio",
  "max_score": 25,
  "theme_id": 9,
  "hint": "Piensa en las capitales europeas",
  "tags": ["geografía", "capitales", "europa"],
  "pairs": [
    {"id": "1", "left_item": "Francia", "right_item": "París"},
    {"id": "2", "left_item": "España", "right_item": "Madrid"},
    {"id": "3", "left_item": "Italia", "right_item": "Roma"},
    {"id": "4", "left_item": "Alemania", "right_item": "Berlín"},
    {"id": "5", "left_item": "Portugal", "right_item": "Lisboa"}
  ]
}
```

---

## 🔄 Ejemplo de Actualización

Para actualizar cualquier pregunta, usa el endpoint `PUT /control-academico/preguntas/{preguntaId}` con el mismo formato JSON, pero **manteniendo el mismo `questionType`**.

```json
{
  "question_type": "multiple_choice_single",
  "question_text": "¿Cuál es la capital de Francia? (Actualizada)",
  "difficulty": "medio",
  "max_score": 15,
  "theme_id": 1,
  "hint": "Ciudad del amor",
  "explanation": "París es la capital de Francia",
  "tags": ["geografía", "europa"],
  "options": [
    {"id": "1", "text": "Londres", "is_correct": false},
    {"id": "2", "text": "París", "is_correct": true},
    {"id": "3", "text": "Roma", "is_correct": false}
  ],
  "correct_option_id": "2"
}
```

---

## 📝 Notas Importantes

1. **theme_id**: Asegúrate de que el ID del tema exista en tu base de datos
2. **question_type**: Debe ser exactamente uno de los 9 tipos soportados
3. **difficulty**: Valores válidos: `facil`, `medio`, `dificil`
4. **tags**: Array de strings para categorizar la pregunta
5. **IDs de opciones**: Deben ser únicos dentro de cada pregunta
6. **⚠️ IMPORTANTE - snake_case**: Todos los campos deben usar snake_case (`question_type`, `is_correct`, `theme_id`, etc.)
7. **⚠️ IMPORTANTE - Campos booleanos**: Todos los campos booleanos como `is_correct` **DEBEN** tener un valor explícito (`true` o `false`). **NO pueden ser null u omitidos**

## 🧪 Flujo de Prueba Completo

```bash
# 1. Crear pregunta
POST /control-academico/preguntas
# Copiar el ID de la respuesta

# 2. Consultar pregunta creada
GET /control-academico/preguntas/{id}

# 3. Actualizar pregunta
PUT /control-academico/preguntas/{id}

# 4. Buscar preguntas
GET /control-academico/preguntas?difficulty=medio

# 5. Clonar pregunta
POST /control-academico/preguntas/{id}/clonar

# 6. Cambiar dificultad
PATCH /control-academico/preguntas/{id}/dificultad?dificultad=dificil

# 7. Eliminar pregunta
DELETE /control-academico/preguntas/{id}
```
