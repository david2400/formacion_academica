package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.config;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class PreguntaConfig {
    
    @Bean
    public Map<String, PreguntaEntityFactory> preguntaFactories(List<PreguntaEntityFactory> factories) {
        return factories.stream()
            .collect(Collectors.toMap(
                PreguntaEntityFactory::getTipoPregunta,
                Function.identity()
            ));
    }
}
