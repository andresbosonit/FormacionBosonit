package com.example.block5properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Component
@PropertySource("classpath:application.properties")
public class Componente implements CommandLineRunner{
    @Value("${greeting}")
    private String greeting1;

    @Value("${my.number}")
    private String number1;

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private final Yaml yaml = new Yaml();

    @Override
    public void run(String... args) throws Exception {
        // Ejercicio 1, leyendo desde application.properties
        System.out.println("Resultado del ejercicio 1:");
        System.out.println("El valor de greeting es: " + greeting1);
        System.out.println("El valor de my.number es: " + number1);
        String property = environment.getProperty("new.property", "new.property no tiene valor");
        System.out.println("El valor de new.property es: " + property);

        // Ejercicio 2, leyendo desde application.yml
        System.out.println("Resultado del ejercicio 2:");
        ClassPathResource resource = new ClassPathResource("application.yml");
        InputStream inputStream = resource.getInputStream();
        Map<String, Object> yamlData = yaml.load(inputStream);

        String greeting2 = (String) yamlData.get("greeting");
        Integer number2 = (Integer) ((Map<String, Object>) yamlData.get("my")).get("number");
        String newProperty2 = null;
        if (yamlData.containsKey("new")) {
            Map<String, Object> newData = (Map<String, Object>) yamlData.get("new");
            if (newData.containsKey("property")) {
                newProperty2 = (String) newData.get("property");
            }
        }
        if(newProperty2 == null){
            newProperty2 = "new.property no tiene valor";
        }
        System.out.println("El valor de greeting es: " + greeting2);
        System.out.println("El valor de my.number es: " + number2);
        System.out.println("El valor de new.property es: " + newProperty2);
    }
}
