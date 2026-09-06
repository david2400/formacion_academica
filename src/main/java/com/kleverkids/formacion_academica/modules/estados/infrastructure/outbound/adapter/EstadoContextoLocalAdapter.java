package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estados.application.output.contexto.EstadoContextoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoContexto;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.CatalogoContextoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.CatalogoEstadoContextoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.CatalogoEstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.CatalogoContextoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.CatalogoEstadoContextoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.CatalogoEstadoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Lado de lectura del catálogo de estados.
 *
 * <p>Es el puerto que consumen los demás módulos para resolver el estado inicial y
 * validar cambios. Todo sale de la base de datos de esta aplicación: no hay
 * llamadas a servicios externos, ni caché, ni degradación que gestionar.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstadoContextoLocalAdapter implements EstadoContextoRepositoryPort {

    private final CatalogoContextoJpaRepository contextoRepository;
    private final CatalogoEstadoContextoJpaRepository parametrizacionRepository;
    private final CatalogoEstadoJpaRepository estadoRepository;

    @Override
    public List<EstadoContexto> listarPorContexto(String contexto, Long idEmpresa) {
        return contextoRepository.findByCodigo(contexto)
                .map(registro -> componer(registro, parametrizacionRepository
                        .findByContextoIdAndIdEmpresaOrderByOrdenAsc(registro.getIdContexto(), empresa(idEmpresa))))
                .orElseGet(List::of);
    }

    @Override
    public Optional<EstadoContexto> obtenerInicial(String contexto, Long idEmpresa) {
        return contextoRepository.findByCodigo(contexto)
                .flatMap(registro -> parametrizacionRepository
                        .findFirstByContextoIdAndIdEmpresaAndEsInicialTrueOrderByOrdenAsc(registro.getIdContexto(),
                                empresa(idEmpresa))
                        .map(parametrizacion -> componer(registro, List.of(parametrizacion)))
                        .flatMap(lista -> lista.stream().findFirst()));
    }

    @Override
    public boolean estaRegistrado(String contexto, Long estadoId, Long idEmpresa) {
        if (estadoId == null) {
            return false;
        }
        return contextoRepository.findByCodigo(contexto)
                .map(registro -> parametrizacionRepository.existsByContextoIdAndEstadoIdAndIdEmpresa(
                        registro.getIdContexto(), estadoId, empresa(idEmpresa)))
                .orElse(false);
    }

    private Long empresa(Long idEmpresa) {
        return idEmpresa != null ? idEmpresa : CatalogoEstadoContextoEntity.EMPRESA_GLOBAL;
    }

    /** Une parametrización y catálogo en una consulta por lote, sin N+1. */
    private List<EstadoContexto> componer(CatalogoContextoEntity contexto,
            List<CatalogoEstadoContextoEntity> parametrizaciones) {
        if (parametrizaciones.isEmpty()) {
            return List.of();
        }

        Map<Long, CatalogoEstadoEntity> catalogo = estadoRepository
                .findAllById(parametrizaciones.stream()
                        .map(CatalogoEstadoContextoEntity::getEstadoId)
                        .distinct()
                        .toList())
                .stream()
                .collect(Collectors.toMap(CatalogoEstadoEntity::getIdEstado, Function.identity()));

        return parametrizaciones.stream()
                .filter(p -> catalogo.containsKey(p.getEstadoId()))
                .map(p -> {
                    CatalogoEstadoEntity estado = catalogo.get(p.getEstadoId());
                    return EstadoContexto.builder()
                            .id(p.getIdParametrizacion())
                            .contexto(contexto.getCodigo())
                            .estadoId(estado.getIdEstado())
                            .codigo(estado.getCodigo())
                            .nombre(estado.getNombre())
                            .descripcion(estado.getDescripcion())
                            .color(estado.getColor())
                            .icono(estado.getIcono())
                            .esInicial(p.getEsInicial())
                            .esFinal(p.getEsFinal())
                            .orden(p.getOrden())
                            .companyId(p.getIdEmpresa())
                            .build();
                })
                .toList();
    }
}
