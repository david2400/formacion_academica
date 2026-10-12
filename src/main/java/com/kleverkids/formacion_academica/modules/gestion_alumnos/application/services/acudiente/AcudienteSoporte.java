package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.cuenta.CuentaUsuarioPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Lógica compartida por varios casos de uso de acudiente (antes métodos privados de
 * AcudienteService). Solo la usan los servicios de este paquete.
 */
@Component
@RequiredArgsConstructor
class AcudienteSoporte {

    private final AcudienteRepositoryPort acudienteRepositoryPort;
    private final CuentaUsuarioPort cuentaUsuarioPort;
    private final PasswordEncoder passwordEncoder;

    /**
     * Evita registrar dos acudientes con el mismo tipo y numero de documento
     * (la tabla acudientes no tiene una restriccion unica para esa
     * combinacion, asi que la validacion se hace aqui).
     */
    void validarDocumentoUnico(String tipoDocumento, String numeroDocumento, Long excluirId) {
        boolean existe = excluirId == null
                ? acudienteRepositoryPort.existePorTipoYNumeroDocumento(tipoDocumento, numeroDocumento)
                : acudienteRepositoryPort.existePorTipoYNumeroDocumentoConIdDiferente(tipoDocumento, numeroDocumento, excluirId);

        if (existe) {
            throw new IllegalArgumentException("Ya existe un acudiente registrado con ese tipo y número de documento");
        }
    }

    /**
     * Hashea (BCrypt) la contraseña en texto plano del request antes de que
     * llegue al mapper/entidad — nunca se persiste en texto plano.
     *
     * <p>Si no se informa contraseña (null o en blanco), se deja el campo en
     * null: en una creación, el acudiente queda sin contraseña; en una
     * actualización, {@code updateEntityFromDto} usa
     * {@code NullValuePropertyMappingStrategy.IGNORE}, así que la contraseña
     * existente no se toca.
     */
    void aplicarPassword(CrearAcudienteDto request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            request.setPassword(null);
            return;
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    /**
     * Si existe una persona en access_control con el documento indicado,
     * valida que esté asignada a la empresa indicada.
     *
     * <p>Es opcional: si no existe ninguna persona con ese documento en
     * access_control, no bloquea la creación/actualización. Si existe pero no
     * está asignada a la empresa indicada, sí bloquea.
     */
    void validarPersonaEmpresa(String tipoDocumento, String numeroDocumento, Long empresaId) {
        cuentaUsuarioPort.buscarPorDocumento(tipoDocumento, numeroDocumento).ifPresent(usuario -> {
            if (!cuentaUsuarioPort.usuarioPerteneceAEmpresa(usuario.getUsuarioId(), empresaId)) {
                throw new IllegalArgumentException(
                        "La persona con el documento indicado existe en access_control pero no está asignada a la empresa indicada");
            }
        });
    }

    void validarPrincipalUnico(Long estudianteId, Long excluirAcudienteId, Boolean esPrincipal) {
        if (Boolean.TRUE.equals(esPrincipal) &&
                acudienteRepositoryPort.existePrincipalParaEstudiante(estudianteId, excluirAcudienteId)) {
            throw new IllegalArgumentException("El estudiante ya tiene un acudiente principal");
        }
    }
}
