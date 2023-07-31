package com.example.block10dockerizeapp.controller;

import com.example.block10dockerizeapp.controller.dto.PersonOutputDto;
import com.example.block10dockerizeapp.application.PersonService;
import com.example.block10dockerizeapp.controller.dto.*;
import com.example.block10dockerizeapp.exceptions.EntityNotFoundException;
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
        PersonOutputDto personOutputDto = servicioPersona.getPerson(id);
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

}
