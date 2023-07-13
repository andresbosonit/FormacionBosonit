package com.example.block5properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class Componente implements CommandLineRunner{
    @Value("${greeting}")
    private String greeting1;

    @Value("${my.number}")
    private String number1;

    @Value("${new.property:new.property no tiene valor}")
    private String property1;

    @Value("${yaml.greeting}")
    private String greeting2;

    @Value("${yaml.my.number}")
    private String number2;

    @Value("${yaml.new.property:new.property no tiene valor}")
    private String property2;

    @Override
    public void run(String... args) throws Exception {
        // Ejercicio 1, leyendo desde application.properties
        System.out.println("Resultado del ejercicio 1:");
        System.out.println("El valor de greeting es: " + greeting1);
        System.out.println("El valor de my.number es: " + number1);
        System.out.println("El valor de new.property es: " + property1);
        System.out.println("Resultado del ejercicio 2:");
        System.out.println("El valor de greeting es: " + greeting2);
        System.out.println("El valor de my.number es: " + number2);
        System.out.println("El valor de new.property es: " + property2);
    }
}
