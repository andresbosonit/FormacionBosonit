package com.example.block7crudvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@SpringBootApplication
public class Block7CrudValidationApplication {
	public static void main(String[] args) {
		SpringApplication.run(Block7CrudValidationApplication.class, args);
	}

}
