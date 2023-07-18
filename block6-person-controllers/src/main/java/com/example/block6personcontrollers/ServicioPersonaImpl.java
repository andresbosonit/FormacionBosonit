package com.example.block6personcontrollers;

import org.springframework.stereotype.Service;

@Service
public class ServicioPersonaImpl implements ServicioPersona {
    private Persona persona;
    @Override
    public Persona crearPersona(String nombre, int edad, String poblacion) {
        persona = new Persona(nombre,edad,poblacion);
        return persona;
    }

    @Override
    public Persona obtenerPersona() {
        return persona;
    }
}
