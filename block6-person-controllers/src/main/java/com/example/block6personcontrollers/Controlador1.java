package com.example.block6personcontrollers;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/controlador1")
public class Controlador1 {
    List<Ciudad> ciudades = new ArrayList<>();

    public Controlador1() {
        ciudades.add(new Ciudad("Logroño",130000));
        ciudades.add(new Ciudad("Madrid",5240000));
        ciudades.add(new Ciudad("Barcelona",6540000));
    }

    // 1 Problema con la codificacion de la ñ
    @GetMapping(value = "/addPersona")
    public Persona capturaHeaders(@RequestHeader("nombre") String nombre,@RequestHeader("poblacion") String poblacion,@RequestHeader("edad") int edad){
        return new ServicioCrearPersona().crearPersona(nombre,edad,poblacion);
    }

    // 2
    @PostMapping(value = "/addCiudad",consumes = "application/json")
    public List<Ciudad> añadirCiudad(@RequestBody Ciudad c){
        ciudades.add(c);
        return ciudades;
    }
}
