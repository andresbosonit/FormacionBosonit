package com.example.block7crudvalidation.controller;

import com.example.block7crudvalidation.application.ProfesorService;
import com.example.block7crudvalidation.controller.dto.*;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
@RestController
@RequestMapping("/profesor")
public class ProfesorController {
    @Autowired
    ProfesorService profesorService;

    @PostMapping
    public ResponseEntity<ProfesorOutputDto> añadirEstudianteAsignatura(@RequestBody ProfesorInputDto profesor){
        URI location = URI.create("/profesor");
        ProfesorOutputDto profesorOutputDto = profesorService.addProfesor(profesor);
        return ResponseEntity.created(location).body(profesorOutputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorOutputDto> obtenerProfesorId(@PathVariable int id) {
        profesorService.getProfesor(id);
        return ResponseEntity.ok().body(profesorService.getProfesor(id));
    }

    @DeleteMapping
    public ResponseEntity<String> borrarProfesorId(@RequestParam int id){
        profesorService.deleteProfesorId(id);
        return ResponseEntity.ok().body("El profesor con id: "+id+" ha sido eliminado");
    }
    @GetMapping
    public Iterable<ProfesorOutputDto> obtenerTodosLosProfesores(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return profesorService.getAllProfesores(pageNumber, pageSize);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfesorOutputDto> actualizarProfesor(@PathVariable Integer id, @RequestBody ProfesorInputDto profesor){
        return ResponseEntity.ok().body(profesorService.updateProfesor(id, profesor));
    }
}
