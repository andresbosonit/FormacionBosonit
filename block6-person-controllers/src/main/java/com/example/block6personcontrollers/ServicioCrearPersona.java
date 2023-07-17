package com.example.block6personcontrollers;

import org.springframework.stereotype.Service;

@Service
public class ServicioCrearPersona {
    public Persona crearPersona(String nombre, int edad, String poblacion) {
        return new Persona(nombre,edad,poblacion);
    }
}
