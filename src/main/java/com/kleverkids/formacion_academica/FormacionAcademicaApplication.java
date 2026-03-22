package com.kleverkids.formacion_academica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.kleverkids.formacion_academica")
public class FormacionAcademicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormacionAcademicaApplication.class, args);
	}

}
