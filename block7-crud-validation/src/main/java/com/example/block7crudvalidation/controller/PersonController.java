package com.example.block7crudvalidation.controller;

import com.example.block7crudvalidation.application.PersonService;
import com.example.block7crudvalidation.controller.dto.*;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persona")
public class PersonController {
    private final ProfesorFeignClient profesorFeignClient;

    public PersonController(ProfesorFeignClient profesorFeignClient) {
        this.profesorFeignClient = profesorFeignClient;
    }
    @Autowired
    PersonService servicioPersona;

    @PostMapping
    public ResponseEntity<PersonOutputDto> añadirPersona(@RequestBody PersonInputDto person){
        URI location = URI.create("/persona");
        PersonOutputDto personOutputDto = servicioPersona.addPerson(person);
        return ResponseEntity.created(location).body(personOutputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDto> obtenerPersonaId(@PathVariable int id,@RequestParam(defaultValue = "simple") String output) {
        PersonOutputDto personOutputDto = servicioPersona.getPersonOutputDto(id, output);
        return ResponseEntity.ok().body(personOutputDto);
    }

    @DeleteMapping
    public ResponseEntity<String> borrarPersonaId(@RequestParam int id){
        servicioPersona.deletePersonId(id);
        return ResponseEntity.ok().body("La persona con id: "+id+" ha sido eliminada");
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<PersonOutputDto>> obtenerPersonasNombre(@RequestParam(defaultValue = "simple") String output,
                                                                       @RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                                       @RequestParam(defaultValue = "4", required = false) int pageSize,
                                                                       @PathVariable String nombre) {
        return ResponseEntity.ok().body(servicioPersona.getPersonsName(pageNumber,pageSize,nombre,output));
    }
    @GetMapping
    public ResponseEntity<List<PersonOutputDto>> obtenerTodasLasPersonas(
            @RequestParam(defaultValue = "simple") String output,
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return ResponseEntity.ok().body(servicioPersona.getAllPersons(pageNumber,pageSize,output));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonOutputDto> actualizarPersona(@PathVariable Integer id, @RequestBody PersonInputDto person){
        return  ResponseEntity.ok().body(servicioPersona.updatePerson(id, person));
    }

    @GetMapping("/profesor/{id}")
    public ResponseEntity<ProfesorOutputDto> getProfesor(@PathVariable int id){
        try{
            return ResponseEntity.ok().body(profesorFeignClient.getProfesor(id));
        }catch (FeignException e){
            throw new EntityNotFoundException("No se encontró el profesor con ID: " + id);
        }
    }
}
