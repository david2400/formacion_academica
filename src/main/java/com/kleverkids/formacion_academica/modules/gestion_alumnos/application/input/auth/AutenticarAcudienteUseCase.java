package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.auth;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginResponseDto;

public interface AutenticarAcudienteUseCase {

    LoginResponseDto autenticar(LoginAcudienteDto request);
}
