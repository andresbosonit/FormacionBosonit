package com.example.block6personcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/controlador1")
public class Controlador1 {
    private final ServicioPersona servicioPersona;
    private final ServicioCiudad servicioCiudad;

    public Controlador1(ServicioPersona servicioPersona, ServicioCiudad servicioCiudad) {
        this.servicioPersona = servicioPersona;
        this.servicioCiudad = servicioCiudad;
    }

    // 1 Problema con la codificacion de la ñ
    @GetMapping(value = "/addPersona")
    public Persona capturaHeaders(@RequestHeader("nombre") String nombre,@RequestHeader("poblacion") String poblacion,@RequestHeader("edad") int edad){
        return this.servicioPersona.crearPersona(nombre,edad,poblacion);
    }

    // 2
    @PostMapping(value = "/addCiudad",consumes = "application/json")
    public ResponseEntity<Ciudad> añadirCiudad(@RequestBody Ciudad c){
        this.servicioCiudad.agregarCiudad(c);
        return ResponseEntity.ok(c);
    }
}
