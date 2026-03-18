package com.kleverkids.formacion_academica.modules.estados.application.input.transicion;

public interface ValidarTransicionUseCase {
    boolean esValida(Long estadoOrigenId, Long estadoDestinoId, Long idModulo);
    boolean esValida(Long estadoOrigenId, Long estadoDestinoId, Long idModulo, Long idEmpresa);
}
