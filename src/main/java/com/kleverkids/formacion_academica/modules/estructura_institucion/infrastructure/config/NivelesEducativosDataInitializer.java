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
            CrearNivelEducativoDto.builder()
                .codigo("PRE_PREJARDIN")
                .nombre("Prejardín")
                .descripcion("Primer nivel de preescolar para niños de 3 años")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            CrearNivelEducativoDto.builder()
                .codigo("PRE_JARDIN")
                .nombre("Jardín")
                .descripcion("Segundo nivel de preescolar para niños de 4 años")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            CrearNivelEducativoDto.builder()
                .codigo("PRE_TRANSICION")
                .nombre("Transición")
                .descripcion("Tercer nivel de preescolar para niños de 5 años")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            
            // BÁSICA PRIMARIA
            CrearNivelEducativoDto.builder()
                .codigo("BAS_1_PRIMERO")
                .nombre("Primero de Primaria")
                .descripcion("Primer grado de educación básica primaria")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            CrearNivelEducativoDto.builder()
                .codigo("BAS_2_SEGUNDO")
                .nombre("Segundo de Primaria")
                .descripcion("Segundo grado de educación básica primaria")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            CrearNivelEducativoDto.builder()
                .codigo("BAS_3_TERCERO")
                .nombre("Tercero de Primaria")
                .descripcion("Tercer grado de educación básica primaria")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            CrearNivelEducativoDto.builder()
                .codigo("BAS_4_CUARTO")
                .nombre("Cuarto de Primaria")
                .descripcion("Cuarto grado de educación básica primaria")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            CrearNivelEducativoDto.builder()
                .codigo("BAS_5_QUINTO")
                .nombre("Quinto de Primaria")
                .descripcion("Quinto grado de educación básica primaria")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            
            // BÁSICA SECUNDARIA
            CrearNivelEducativoDto.builder()
                .codigo("BAS_6_SEXTO")
                .nombre("Sexto de Básica")
                .descripcion("Primer grado de educación básica secundaria")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            CrearNivelEducativoDto.builder()
                .codigo("BAS_7_SEPTIMO")
                .nombre("Séptimo de Básica")
                .descripcion("Segundo grado de educación básica secundaria")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            CrearNivelEducativoDto.builder()
                .codigo("BAS_8_OCTAVO")
                .nombre("Octavo de Básica")
                .descripcion("Tercer grado de educación básica secundaria")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            CrearNivelEducativoDto.builder()
                .codigo("BAS_9_NOVENO")
                .nombre("Noveno de Básica")
                .descripcion("Cuarto grado de educación básica secundaria")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            
            // MEDIA
            CrearNivelEducativoDto.builder()
                .codigo("MED_10_DECIMO")
                .nombre("Décimo")
                .descripcion("Primer grado de educación media")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build(),
            CrearNivelEducativoDto.builder()
                .codigo("MED_11_ONCE")
                .nombre("Once")
                .descripcion("Segundo grado de educación media")
                .nivelSuperiorId(null)
                .eliminado(true)
                .build()
        );
    }
}
