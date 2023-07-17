package com.example.block6personcontrollers;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/controlador2")
public class Controlador2 {
    @Autowired
    Controlador1 c1;

    // 1
    @GetMapping(value = "/getPersona")
    public Persona devolverPersona(@RequestHeader("nombre") String nombre, @RequestHeader("poblacion") String poblacion, @RequestHeader("edad") int edad){
        Persona p = c1.capturaHeaders(nombre,poblacion,edad);
        p.setEdad(p.getEdad()*2);
        return p;
    }

    // 2
    @GetMapping(value = "/getCiudades")
    public List<Ciudad> devolverPersona(){
        return c1.ciudades;
    }
}
