package com.kleverkids.formacion_academica.modules.usuarios.application.services;

import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorConsultaPort;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorRepositoryPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.domain.model.Profesor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfesorServiceTest {

    /** Repositorio en memoria con borrado lógico. */
    static class Repo implements ProfesorRepositoryPort {
        final Map<Long, Profesor> filas = new HashMap<>();
        final Set<Long> eliminados = new HashSet<>();
        long secuencia = 0;

        public Profesor guardar(Profesor p) {
            if (p.getId() == null) p.setId(++secuencia);
            filas.put(p.getId(), p);
            return p;
        }
        public Optional<Profesor> buscarPorId(Long id) {
            return eliminados.contains(id) ? Optional.empty() : Optional.ofNullable(filas.get(id));
        }
        public boolean existe(Long u, Long e) {
            return filas.values().stream().anyMatch(p -> !eliminados.contains(p.getId()) && p.getUsuarioId().equals(u) && p.getEmpresaId().equals(e));
        }
        public Optional<Long> restaurarEliminado(Profesor d) {
            return filas.values().stream().filter(p -> eliminados.contains(p.getId()) && p.getUsuarioId().equals(d.getUsuarioId())
                    && p.getEmpresaId().equals(d.getEmpresaId())).findFirst().map(p -> {
                eliminados.remove(p.getId());
                p.setActivo(true);
                p.setObservaciones(d.getObservaciones());
                return p.getId();
            });
        }
        public void eliminar(Long id) { eliminados.add(id); }
    }

    final Repo repo = new Repo();
    final Set<Long> usuariosDeEmpresa10 = new HashSet<>(Set.of(1L, 2L));

    final ProfesorConsultaPort consulta = new ProfesorConsultaPort() {
        public Page<ProfesorDto> listar(FiltroProfesoresDto f, Pageable p) { return Page.empty(); }
        public Optional<ProfesorDto> obtener(Long id) {
            return repo.buscarPorId(id).map(p -> ProfesorDto.builder().id(p.getId()).usuarioId(p.getUsuarioId())
                    .empresaId(p.getEmpresaId()).activo(p.isActivo()).observaciones(p.getObservaciones()).build());
        }
        public List<UsuarioDisponibleDto> usuariosDisponibles(Long e, String t, int l) { return List.of(); }
        public List<ProfesorResumenDto> resumen(Long e, Collection<Long> ids) { return List.of(); }
        public boolean usuarioPerteneceAEmpresa(Long u, Long e) { return e == 10L && usuariosDeEmpresa10.contains(u); }
    };

    final ProfesorService service = new ProfesorService(repo, consulta);

    private CrearProfesorDto alta(long usuario) {
        return CrearProfesorDto.builder().usuarioId(usuario).empresaId(10L).observaciones("  B1  ").build();
    }

    @Test
    void creaActivoYLimpiaTexto() {
        ProfesorDto p = service.crear(alta(1));
        assertThat(p.isActivo()).isTrue();
        assertThat(p.getObservaciones()).isEqualTo("B1");
    }

    @Test
    void rechazaUsuarioDeOtraEmpresaYDuplicados() {
        assertThatThrownBy(() -> service.crear(alta(9))).isInstanceOf(IllegalArgumentException.class);
        service.crear(alta(1));
        assertThatThrownBy(() -> service.crear(alta(1))).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void eliminarYVolverACrearRecuperaLaMismaFila() {
        ProfesorDto p = service.crear(alta(2));
        service.cambiarEstado(p.getId(), false);
        service.eliminar(p.getId());
        ProfesorDto otraVez = service.crear(alta(2));
        assertThat(otraVez.getId()).isEqualTo(p.getId());
        assertThat(otraVez.isActivo()).isTrue();
        assertThat(repo.filas).hasSize(1);
    }

    @Test
    void resumenAcotado() {
        assertThat(service.resumir(10L, List.of())).isEmpty();
        List<Long> muchos = new ArrayList<>();
        for (long i = 0; i < 201; i++) muchos.add(i);
        assertThatThrownBy(() -> service.resumir(10L, muchos)).isInstanceOf(IllegalArgumentException.class);
    }
}
