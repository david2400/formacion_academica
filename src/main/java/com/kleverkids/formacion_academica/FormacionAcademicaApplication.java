package com.kleverkids.formacion_academica;

import com.kleverkids.formacion_academica.modules.estructura_institucion.EstructuraInstitucionModuleConfig;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.GestionAlumnosModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = "com.kleverkids.formacion_academica")
@Import({EstructuraInstitucionModuleConfig.class, GestionAlumnosModuleConfig.class})
public class FormacionAcademicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormacionAcademicaApplication.class, args);
	}

}
