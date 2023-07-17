package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionControlador {
    @Bean
    @Qualifier("bean1")
    public Persona personaBean1() {
        Persona persona = new Persona();
        persona.setNombre("bean1");
        return persona;
    }

    @Bean
    @Qualifier("bean2")
    public Persona personaBean2() {
        Persona persona = new Persona();
        persona.setNombre("bean2");
        return persona;
    }

    @Bean
    @Qualifier("bean3")
    public Persona personaBean3() {
        Persona persona = new Persona();
        persona.setNombre("bean3");
        return persona;
    }
}
