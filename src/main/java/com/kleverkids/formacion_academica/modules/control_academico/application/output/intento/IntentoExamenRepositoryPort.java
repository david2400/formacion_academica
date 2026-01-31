package com.kleverkids.formacion_academica.modules.control_academico.application.output.intento;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;

import java.util.List;
import java.util.UUID;

public interface IntentoExamenRepositoryPort {

    IntentoExamenDto iniciar(IniciarIntentoExamenDto request);

    RespuestaIntentoDto registrarRespuesta(RegistrarRespuestaIntentoDto request);

    IntentoExamenDto finalizar(FinalizarIntentoExamenDto request);

    List<IntentoExamenDto> listarPorEstudiante(UUID examenId, UUID estudianteId);
}
