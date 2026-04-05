package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.config;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.NivelEducativoService;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.CrearNivelEducativoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class NivelesEducativosDataInitializer implements CommandLineRunner {

    private final NivelEducativoService nivelEducativoService;

    @Override
    public void run(String... args) throws Exception {
        if (debeInicializarDatos()) {
            inicializarNivelesEducativos();
            log.info("✅ Niveles educativos inicializados correctamente según Circular Colombia 2025");
        }
    }

    private boolean debeInicializarDatos() {
        return nivelEducativoService.listarTodos().isEmpty();
    }

    private void inicializarNivelesEducativos() {
        List<CrearNivelEducativoDto> niveles = getNivelesEducativosCircular2025();
        
        for (CrearNivelEducativoDto nivel : niveles) {
            try {
                nivelEducativoService.crear(nivel);
                log.debug("📚 Nivel educativo creado: {}", nivel.getNombre());
            } catch (Exception e) {
                log.error("❌ Error creando nivel educativo {}: {}", nivel.getCodigo(), e.getMessage());
            }
        }
    }

    private List<CrearNivelEducativoDto> getNivelesEducativosCircular2025() {
        return List.of(
            // PREESCOLAR
            new CrearNivelEducativoDto(
                "PRE_PREJARDIN", 
                "Prejardín", 
                "Primer nivel de preescolar para niños de 3 años", 
//                1,
                null,
                    true
//                "PREESCOLAR"
            ),
            new CrearNivelEducativoDto(
                "PRE_JARDIN", 
                "Jardín", 
                "Segundo nivel de preescolar para niños de 4 años", 
//                2,
                null,
                    true
//                "PREESCOLAR"
            ),
            new CrearNivelEducativoDto(
                "PRE_TRANSICION", 
                "Transición", 
                "Tercer nivel de preescolar para niños de 5 años", 
//                3,
                null,
                    true
//                "PREESCOLAR"
            ),
            
            // BÁSICA PRIMARIA
            new CrearNivelEducativoDto(
                "BAS_1_PRIMERO", 
                "Primero de Primaria", 
                "Primer grado de educación básica primaria", 
//                1,
                null,
                    true
//                "BASICA"
            ),
            new CrearNivelEducativoDto(
                "BAS_2_SEGUNDO", 
                "Segundo de Primaria", 
                "Segundo grado de educación básica primaria", 
//                2,
                null,
                    true
//                "BASICA"
            ),
            new CrearNivelEducativoDto(
                "BAS_3_TERCERO", 
                "Tercero de Primaria", 
                "Tercer grado de educación básica primaria", 
//                3,
                null,
                    true
//                "BASICA"
            ),
            new CrearNivelEducativoDto(
                "BAS_4_CUARTO", 
                "Cuarto de Primaria", 
                "Cuarto grado de educación básica primaria", 
//                4,
                null,
                    true
//                "BASICA"
            ),
            new CrearNivelEducativoDto(
                "BAS_5_QUINTO", 
                "Quinto de Primaria", 
                "Quinto grado de educación básica primaria", 
//                5,
                null,
                    true
//                "BASICA"
            ),
            
            // BÁSICA SECUNDARIA
            new CrearNivelEducativoDto(
                "BAS_6_SEXTO", 
                "Sexto de Básica", 
                "Primer grado de educación básica secundaria", 
//                6,
                null,
                    true
//                "BASICA"
            ),
            new CrearNivelEducativoDto(
                "BAS_7_SEPTIMO", 
                "Séptimo de Básica", 
                "Segundo grado de educación básica secundaria", 
//                7,
                null,
                    true
//                "BASICA"
            ),
            new CrearNivelEducativoDto(
                "BAS_8_OCTAVO", 
                "Octavo de Básica", 
                "Tercer grado de educación básica secundaria", 
//                8,
                null,
                    true
//                "BASICA"
            ),
            new CrearNivelEducativoDto(
                "BAS_9_NOVENO", 
                "Noveno de Básica", 
                "Cuarto grado de educación básica secundaria", 
//                9,
                null,
                    true
//                "BASICA"
            ),
            
            // MEDIA
            new CrearNivelEducativoDto(
                "MED_10_DECIMO", 
                "Décimo", 
                "Primer grado de educación media", 
//                10,
                null,
                    true
//                "MEDIA"
            ),
            new CrearNivelEducativoDto(
                "MED_11_ONCE", 
                "Once", 
                "Segundo grado de educación media", 
//                11,
                null,
                true
//                "MEDIA"
            )
        );
    }
}
