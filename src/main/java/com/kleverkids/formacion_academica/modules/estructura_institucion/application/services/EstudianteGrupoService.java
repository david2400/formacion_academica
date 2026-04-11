//package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services;
//
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.AsignarEstudianteGrupoUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.CambiarEstadoEstudianteGrupoUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ConsultarEstudianteGrupoUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.EliminarEstudianteGrupoUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ListarEstudianteGruposUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ListarEstudiantesPorGrupoUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
// @Service
// public class EstudianteGrupoService implements AsignarEstudianteGrupoUseCase,
//         CambiarEstadoEstudianteGrupoUseCase,
//         ConsultarEstudianteGrupoUseCase,
//         EliminarEstudianteGrupoUseCase,
//         ListarEstudianteGruposUseCase,
//         ListarEstudiantesPorGrupoUseCase {
//
//     private final EstudianteGrupoRepositoryPort repositoryPort;
//
//     public EstudianteGrupoService(EstudianteGrupoRepositoryPort repositoryPort) {
//         this.repositoryPort = repositoryPort;
//     }
//
//     @Override
//     public EstudianteGrupo asignar(AsignarEstudianteGrupoDto request) {
//         return repositoryPort.asignar(request);
//     }
//
//     @Override
//     public EstudianteGrupo cambiarEstado(CambiarEstadoEstudianteGrupoDto request) {
//         return repositoryPort.cambiarEstado(request);
//     }
//
//     @Override
//     public List<EstudianteGrupo> listar(Long grupoId) {
//         return repositoryPort.listarPorGrupo(grupoId);
//     }
//
//     @Override
//     public EstudianteGrupo consultarPorId(Long estudianteGrupoId) {
//         return repositoryPort.consultarPorId(estudianteGrupoId);
//     }
//
//     @Override
//     public void eliminar(Long estudianteGrupoId) {
//         repositoryPort.eliminar(estudianteGrupoId);
//     }
//
//     @Override
//     public List<EstudianteGrupo> listar() {
//         return repositoryPort.listar();
//     }
// }
