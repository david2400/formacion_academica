package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estados.application.output.catalogo.CatalogoEstadosAdminPort;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.ActualizarParametrizacionDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.CrearEstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.HabilitarEstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.RegistrarContextoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.model.ContextoEstado;
import com.kleverkids.formacion_academica.modules.estados.domain.model.Estado;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoContexto;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.CatalogoContextoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.CatalogoEstadoContextoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.CatalogoEstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.CatalogoContextoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.CatalogoEstadoContextoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.CatalogoEstadoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class CatalogoEstadosAdminJpaAdapter implements CatalogoEstadosAdminPort {

    private final CatalogoEstadoJpaRepository estadoRepository;
    private final CatalogoContextoJpaRepository contextoRepository;
    private final CatalogoEstadoContextoJpaRepository parametrizacionRepository;

    /**
     * Prefijo de los códigos de contexto. Se conserva aunque hoy solo haya una
     * aplicación: mantiene los códigos estables si el catálogo se comparte algún día.
     */
    @Value("${estados.aplicacion:formacion_academica}")
    private String aplicacion;

    // ── Catálogo ────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<Estado> listarEstados() {
        return estadoRepository.findAllByOrderByOrdenAsc().stream().map(this::aDominio).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estado> buscarEstadoPorId(Long id) {
        return estadoRepository.findById(id).map(this::aDominio);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estado> buscarEstadoPorCodigo(String codigo) {
        return estadoRepository.findByCodigo(codigo).map(this::aDominio);
    }

    @Override
    public Estado crearEstado(CrearEstadoDto request) {
        String codigo = normalizar(request.getCodigo());
        if (estadoRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un estado con el código " + codigo);
        }
        CatalogoEstadoEntity entity = new CatalogoEstadoEntity();
        entity.setCodigo(codigo);
        aplicar(entity, request);
        return aDominio(estadoRepository.save(entity));
    }

    @Override
    public Estado actualizarEstado(Long id, CrearEstadoDto request) {
        CatalogoEstadoEntity entity = estadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));
        aplicar(entity, request);
        return aDominio(estadoRepository.save(entity));
    }

    /**
     * Borra un estado, salvo que siga habilitado en algún contexto.
     *
     * <p>Sin esta guarda quedarían registros apuntando a un estado inexistente. El
     * borrado tiene que empezar por quitarlo de los contextos.
     */
    @Override
    public void eliminarEstado(Long id) {
        if (!estadoRepository.existsById(id)) {
            throw new IllegalArgumentException("Estado no encontrado");
        }

        List<String> enUso = parametrizacionRepository.findByEstadoId(id).stream()
                .map(CatalogoEstadoContextoEntity::getContextoId)
                .distinct()
                .map(contextoRepository::findById)
                .flatMap(Optional::stream)
                .map(CatalogoContextoEntity::getCodigo)
                .sorted()
                .toList();

        if (!enUso.isEmpty()) {
            throw new IllegalStateException("No se puede eliminar el estado porque está habilitado en: "
                    + String.join(", ", enUso) + ". Quítalo primero de esos contextos.");
        }

        estadoRepository.deleteById(id);
    }

    // ── Contextos ───────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<ContextoEstado> listarContextos() {
        return contextoRepository.findAllByOrderByCodigoAsc().stream().map(this::aDominio).toList();
    }

    @Override
    public ContextoEstado registrarContexto(RegistrarContextoDto request) {
        String modulo = normalizar(request.getModulo());
        String entidad = normalizar(request.getEntidad());

        // Idempotente: registrar la misma pareja devuelve el contexto existente, así
        // el arranque de cada módulo puede repetirlo sin fallar.
        Optional<CatalogoContextoEntity> existente = contextoRepository.findByModuloAndEntidad(modulo, entidad);
        if (existente.isPresent()) {
            return aDominio(existente.get());
        }

        CatalogoContextoEntity entity = new CatalogoContextoEntity();
        entity.setModulo(modulo);
        entity.setEntidad(entidad);
        entity.setCodigo(String.join(".", normalizar(aplicacion), modulo, entidad));
        entity.setNombre(request.getNombre() != null && !request.getNombre().isBlank()
                ? request.getNombre()
                : entidad);
        entity.setDescripcion(request.getDescripcion());

        return aDominio(contextoRepository.save(entity));
    }

    // ── Parametrización ─────────────────────────────────────────────────────

    @Override
    public EstadoContexto habilitarEstado(String codigoContexto, HabilitarEstadoDto request) {
        CatalogoContextoEntity contexto = contextoRepository.findByCodigo(codigoContexto)
                .orElseThrow(() -> new IllegalArgumentException("El contexto '" + codigoContexto
                        + "' no está registrado. Regístralo antes de parametrizarlo."));

        CatalogoEstadoEntity estado = estadoRepository.findById(request.getEstadoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el estado " + request.getEstadoId() + " en el catálogo"));

        Long idEmpresa = empresa(request.getIdEmpresa());
        boolean esInicial = Boolean.TRUE.equals(request.getEsInicial());
        boolean esFinal = Boolean.TRUE.equals(request.getEsFinal());
        int orden = request.getOrden() != null
                ? request.getOrden()
                : siguienteOrden(contexto.getIdContexto(), idEmpresa);

        CatalogoEstadoContextoEntity entity = parametrizacionRepository
                .findByContextoIdAndEstadoIdAndIdEmpresa(contexto.getIdContexto(), estado.getIdEstado(), idEmpresa)
                .orElseGet(CatalogoEstadoContextoEntity::new);

        entity.setContextoId(contexto.getIdContexto());
        entity.setEstadoId(estado.getIdEstado());
        entity.setIdEmpresa(idEmpresa);
        entity.setEsInicial(esInicial);
        entity.setEsFinal(esFinal);
        entity.setOrden(orden);

        CatalogoEstadoContextoEntity guardada = parametrizacionRepository.save(entity);

        if (esInicial) {
            parametrizacionRepository.desmarcarInicialesSalvo(contexto.getIdContexto(), idEmpresa,
                    guardada.getIdParametrizacion());
        }

        return componer(contexto, guardada, estado);
    }

    @Override
    public EstadoContexto actualizarParametrizacion(Long id, ActualizarParametrizacionDto request) {
        CatalogoEstadoContextoEntity entity = parametrizacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parametrización no encontrada"));

        if (request.getEsInicial() != null) {
            entity.setEsInicial(request.getEsInicial());
        }
        if (request.getEsFinal() != null) {
            entity.setEsFinal(request.getEsFinal());
        }
        if (request.getOrden() != null) {
            entity.setOrden(request.getOrden());
        }

        CatalogoEstadoContextoEntity guardada = parametrizacionRepository.save(entity);

        if (Boolean.TRUE.equals(guardada.getEsInicial())) {
            parametrizacionRepository.desmarcarInicialesSalvo(guardada.getContextoId(), guardada.getIdEmpresa(),
                    guardada.getIdParametrizacion());
        }

        CatalogoContextoEntity contexto = contextoRepository.findById(guardada.getContextoId())
                .orElseThrow(() -> new IllegalStateException("El contexto asociado ya no existe"));
        CatalogoEstadoEntity estado = estadoRepository.findById(guardada.getEstadoId())
                .orElseThrow(() -> new IllegalStateException("El estado asociado ya no existe en el catálogo"));

        return componer(contexto, guardada, estado);
    }

    @Override
    public void deshabilitarEstado(Long id) {
        if (!parametrizacionRepository.existsById(id)) {
            throw new IllegalArgumentException("Parametrización no encontrada");
        }
        parametrizacionRepository.deleteById(id);
    }

    // ── Helpers ─────────────────────────────────────────────────────────────

    private Long empresa(Long idEmpresa) {
        return idEmpresa != null ? idEmpresa : CatalogoEstadoContextoEntity.EMPRESA_GLOBAL;
    }

    /** Minúsculas y guion bajo, para que los códigos sean estables. */
    private String normalizar(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "_");
    }

    private int siguienteOrden(Long contextoId, Long idEmpresa) {
        return parametrizacionRepository.findByContextoIdAndIdEmpresaOrderByOrdenAsc(contextoId, idEmpresa).stream()
                .map(CatalogoEstadoContextoEntity::getOrden)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;
    }

    private void aplicar(CatalogoEstadoEntity entity, CrearEstadoDto request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setColor(request.getColor());
        entity.setIcono(request.getIcono());
        entity.setOrden(request.getOrden() != null ? request.getOrden() : 0);
    }

    private Estado aDominio(CatalogoEstadoEntity entity) {
        return Estado.builder()
                .id(entity.getIdEstado())
                .codigo(entity.getCodigo())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .color(entity.getColor())
                .icono(entity.getIcono())
                .orden(entity.getOrden())
                .build();
    }

    private ContextoEstado aDominio(CatalogoContextoEntity entity) {
        return ContextoEstado.builder()
                .id(entity.getIdContexto())
                .modulo(entity.getModulo())
                .entidad(entity.getEntidad())
                .codigo(entity.getCodigo())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .build();
    }

    private EstadoContexto componer(CatalogoContextoEntity contexto, CatalogoEstadoContextoEntity parametrizacion,
            CatalogoEstadoEntity estado) {
        return EstadoContexto.builder()
                .id(parametrizacion.getIdParametrizacion())
                .contexto(contexto.getCodigo())
                .estadoId(estado.getIdEstado())
                .codigo(estado.getCodigo())
                .nombre(estado.getNombre())
                .descripcion(estado.getDescripcion())
                .color(estado.getColor())
                .icono(estado.getIcono())
                .esInicial(parametrizacion.getEsInicial())
                .esFinal(parametrizacion.getEsFinal())
                .orden(parametrizacion.getOrden())
                .companyId(parametrizacion.getIdEmpresa())
                .build();
    }
}
