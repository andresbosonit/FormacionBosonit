package com.example.block5profiles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class SpringProfilesApp implements CommandLineRunner {

	@Autowired
	private final AppConfig appConfig;

	public static void main(String[] args) {
		SpringApplication.run(SpringProfilesApp.class, args);
	}

	@Override
	public void run(String... args)
	{
		log.info("My app name: {}. Environment: {}. BD.URL: {}", appConfig.getName(), appConfig.getEnvironment(), appConfig.getBd());
	}
}
