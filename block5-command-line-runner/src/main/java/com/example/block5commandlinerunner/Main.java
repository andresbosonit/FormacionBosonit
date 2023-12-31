package com.example.block5commandlinerunner;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@Slf4j
public class Main implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		// Llamando a la tercera función desde el main
		funcionTercera("Jose", "Maria");
	}
	@Bean
	CommandLineRunner ejecutame() {
		return p ->
		{
			System.out.println("Linea a ejecutar cuando arranque");
		};
	}

	@Bean
	CommandLineRunner funcionSecundaria() {
		return p ->
		{
			System.out.println("Hola desde la clase secundaria");
		};
	}

	/* 1) Tercera función sin modificar
	@Bean
	CommandLineRunner funcionTercera(String a, String b) {
		return p ->
		{
			System.out.println("Hola desde la clase tercera " + a + b);
		};
	}*/

	// 2) Modificar la tercera función para que imprima los valores pasados como parámetro al ejecutar el programa.
	public static void funcionTercera(String a, String b) {
		System.out.println("Hola desde la clase tercera, " + a + " " + b);
	}

	@Override
	public void run(String... args) throws Exception {
		// Llamando a la función desde el run
		funcionTercera("Jose", "Maria");
	}

	@PostConstruct
	public void funcionInicial() {
		System.out.println("Hola desde la clase inicial");
	}
}
