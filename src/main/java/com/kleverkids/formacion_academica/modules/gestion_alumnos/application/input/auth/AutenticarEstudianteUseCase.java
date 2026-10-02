package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.auth;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginResponseDto;

public interface AutenticarEstudianteUseCase {

    LoginResponseDto autenticar(LoginEstudianteDto request);
}
