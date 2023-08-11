package com.example.block7crudvalidation;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.block7crudvalidation.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.repository.PersonRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;


@SpringBootApplication
@EnableFeignClients
public class Block7CrudValidationApplication {
	@Autowired
	PersonRepository personRepository;
	public static void main(String[] args) {
		SpringApplication.run(Block7CrudValidationApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("*/getall")
						.allowedOrigins("https://cdpn.io")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
						.allowedHeaders("*")
						.allowCredentials(true);
				registry.addMapping("*/addperson")
						.allowedOrigins("https://cdpn.io")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}

	@PostConstruct
	public void populateDb() {
		personRepository.save(new Person(new PersonInputDto("andres", "1234", true, "andres",
				"Fernandez", "lolo@aa.es", "lolo@lolo.es", "Logroño",
				true, new Date(), "lolo.jpg", new Date())));
	}
}
