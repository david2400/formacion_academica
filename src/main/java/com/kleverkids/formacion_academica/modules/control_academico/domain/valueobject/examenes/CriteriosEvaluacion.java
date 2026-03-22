package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record CriteriosEvaluacion(
    String nombre,
    String descripcion,
    Double pesoTotal,
    List<Criterio> criterios,
    Map<String, Object> parametrosAdicionales
) {
    
    public CriteriosEvaluacion {
        Objects.requireNonNull(nombre, "Nombre no puede ser nulo");
        Objects.requireNonNull(criterios, "Criterios no pueden ser nulos");
        if (pesoTotal == null || pesoTotal <= 0 || pesoTotal > 100) {
            throw new IllegalArgumentException("Peso total debe estar entre 0 y 100");
        }
        if (criterios.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un criterio");
        }
        validarPesos();
    }
    
    private void validarPesos() {
        double sumaPesos = criterios.stream()
            .mapToDouble(Criterio::peso)
            .sum();
        
        if (Math.abs(sumaPesos - pesoTotal) > 0.01) {
            throw new IllegalArgumentException("La suma de pesos de criterios debe igualar el peso total");
        }
    }
    
    public static CriteriosEvaluacion simple(String nombre, String descripcion, Double pesoTotal) {
        return new CriteriosEvaluacion(
            nombre,
            descripcion,
            pesoTotal,
            List.of(new Criterio("Criterio principal", pesoTotal, null)),
            Map.of()
        );
    }
    
    public Criterio getCriterioPrincipal() {
        return criterios.stream()
            .filter(c -> c.peso().equals(pesoTotal))
            .findFirst()
            .orElse(criterios.get(0));
    }
    
    public boolean tieneCriteriosMultiples() {
        return criterios.size() > 1;
    }
    
    public record Criterio(
        String nombre,
        Double peso,
        String descripcion
    ) {
        public Criterio {
            Objects.requireNonNull(nombre, "Nombre de criterio no puede ser nulo");
            if (peso == null || peso <= 0) {
                throw new IllegalArgumentException("Peso de criterio debe ser positivo");
            }
        }
    }
}
