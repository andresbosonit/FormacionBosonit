package com.example.block5logging;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@SpringBootApplication
@Slf4j
public class MainApplication implements CommandLineRunner {
	public static void main(String[] args) throws IOException {
		clearLogFile();
		SpringApplication.run(MainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.trace("Mensaje a nivel de TRACE");
		log.debug("Mensaje a nivel de DEBUG");
		log.info("Mensaje a nivel de INFO");
		log.warn("Mensaje a nivel de WARNING");
		log.error("Mensaje a nivel de ERROR");
	}

	private static void clearLogFile() throws IOException {
		String logFilePath = "../block5-logging/src/main/resources/logs/myapplication.log";
		Path logPath = Path.of(logFilePath);
		Files.writeString(logPath, "", StandardOpenOption.TRUNCATE_EXISTING);
	}


}
