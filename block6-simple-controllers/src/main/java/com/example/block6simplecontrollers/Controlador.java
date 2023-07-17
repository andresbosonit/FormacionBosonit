package com.example.block6simplecontrollers;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controlador {
    @GetMapping(value = "/user/{nombre}")
    public String metodo1(@PathVariable String nombre){
        return "Hola " + nombre;
    }

    @PostMapping(value = "/useradd",consumes = "application/json")
    public Persona metodo2(@RequestBody Persona p){
        return new Persona(p.getNombre(),p.getEdad()+1,p.getPoblacion());
    }
}
