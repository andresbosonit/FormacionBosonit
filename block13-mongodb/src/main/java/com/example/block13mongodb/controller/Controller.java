package com.example.block13mongodb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/persona")
public class Controller {
/*
    @Autowired
    ServicioPersonaImpl servicioPersona;

    @PostMapping
    public ResponseEntity<PersonOutputDto> añadirPersona(@RequestBody PersonInputDto person) {
        URI location = URI.create("/persona");
        return ResponseEntity.created(location).body(servicioPersona.añadirPersona(person));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDto> obtenerPersonaId(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(servicioPersona.consultarPersonaId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> borrarPersonaId(@RequestParam int id) {
        try {
            servicioPersona.borrarPesona(id);
            return ResponseEntity.ok().body("Person with id: "+id+" was deleted");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public Iterable<PersonOutputDto> obtenerPersonasNombre(@PathVariable String nombre) {
        return servicioPersona.consultarPersonaNombre(nombre);
    }
    @GetMapping
    public Iterable<PersonOutputDto> obtenerTodasLasPersonas(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return servicioPersona.consultarTodosLosRegistros(pageNumber, pageSize);
    }

    @PutMapping
    public ResponseEntity<PersonOutputDto> actualizarPersona(@RequestBody PersonInputDto person) {
        try {
            PersonOutputDto personaEncontrada = servicioPersona.consultarPersonaId(person.getId());
            if(person.getEdad() != null){
                person.setEdad(person.getEdad());
            }else{
                person.setEdad(personaEncontrada.getEdad());
            }
            if(person.getNombre() != null){
                person.setNombre(person.getNombre());
            }else{
                person.setNombre(personaEncontrada.getNombre());
            }
            if(person.getPoblacion() != null){
                person.setPoblacion(person.getPoblacion());
            }else{
                person.setPoblacion(personaEncontrada.getPoblacion());
            }
            return  ResponseEntity.ok().body(servicioPersona.añadirPersona(person));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }*/
}
