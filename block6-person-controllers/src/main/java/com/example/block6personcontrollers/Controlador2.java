package com.example.block6personcontrollers;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/controlador2")
public class Controlador2 {

    private final ServicioPersona servicioPersona;
    private final ServicioCiudad servicioCiudad;
    public Controlador2(ServicioPersona servicioPersona, ServicioCiudad servicioCiudad) {
        this.servicioPersona = servicioPersona;
        this.servicioCiudad = servicioCiudad;
    }
    // 1
    @GetMapping(value = "/getPersona")
    public Persona devolverPersona(){
        Persona p = this.servicioPersona.obtenerPersona();
        return new Persona(p.getNombre(),p.getEdad()*2,p.getPoblacion());
    }

    // 2
    @GetMapping(value = "/getCiudades")
    public List<Ciudad> devolverCiudades(){
        return this.servicioCiudad.obtenerCiudades();
    }
}
