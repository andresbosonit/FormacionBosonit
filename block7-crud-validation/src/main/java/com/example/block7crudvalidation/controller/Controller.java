package com.example.block7crudvalidation.controller;

import com.example.block7crudvalidation.application.PersonServiceImpl;
import com.example.block7crudvalidation.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/persona")
public class Controller {
    @Autowired
    PersonServiceImpl servicioPersona;

    @PostMapping
    public ResponseEntity<PersonOutputDto> añadirPersona(@RequestBody PersonInputDto person) throws Exception {
        URI location = URI.create("/persona");
        return ResponseEntity.created(location).body(servicioPersona.addPerson(person));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDto> obtenerPersonaId(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(servicioPersona.getPerson(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> borrarPersonaId(@RequestParam int id) {
        try {
            servicioPersona.deletePersonId(id);
            return ResponseEntity.ok().body("Person with id: "+id+" was deleted");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public Iterable<PersonOutputDto> obtenerPersonasNombre(@PathVariable String nombre) {
        return servicioPersona.getPersonsName(nombre);
    }
    @GetMapping
    public Iterable<PersonOutputDto> obtenerTodasLasPersonas(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return servicioPersona.getAllPersons(pageNumber, pageSize);
    }

    @PutMapping
    public ResponseEntity<PersonOutputDto> actualizarPersona(@RequestBody PersonInputDto person) {
        try {
            PersonOutputDto personaEncontrada = servicioPersona.getPerson(person.getIdPersona());
            person.setUsuario(Objects.requireNonNullElse(person.getUsuario(), personaEncontrada.getUsuario()));
            person.setPassword(Objects.requireNonNullElse(person.getPassword(), personaEncontrada.getPassword()));
            person.setName(Objects.requireNonNullElse(person.getName(), personaEncontrada.getName()));
            person.setSurname(Objects.requireNonNullElse(person.getSurname(), personaEncontrada.getSurname()));
            person.setCompanyEmail(Objects.requireNonNullElse(person.getCompanyEmail(), personaEncontrada.getCompanyEmail()));
            person.setPersonalEmail(Objects.requireNonNullElse(person.getPersonalEmail(), personaEncontrada.getPersonalEmail()));
            person.setCity(Objects.requireNonNullElse(person.getCity(), personaEncontrada.getCity()));
            person.setActive(Objects.requireNonNullElse(person.getActive(), personaEncontrada.getActive()));
            person.setCreatedDate(Objects.requireNonNullElse(person.getCreatedDate(), personaEncontrada.getCreatedDate()));
            person.setImageUrl(Objects.requireNonNullElse(person.getImageUrl(), personaEncontrada.getImageUrl()));
            person.setTerminationDate(Objects.requireNonNullElse(person.getTerminationDate(), personaEncontrada.getTerminationDate()));/*
            if(person.getUsuario() != null){
                person.setUsuario(person.getUsuario());
            }else{
                person.setUsuario(personaEncontrada.getUsuario());
            }
            if(person.getPassword() != null){
                person.setPassword(person.getPassword());
            }else{
                person.setPassword(personaEncontrada.getPassword());
            }
            if(person.getName() != null){
                person.setName(person.getName());
            }else{
                person.setName(personaEncontrada.getName());
            }
            if(person.getSurname() != null){
                person.setSurname(person.getSurname());
            }else{
                person.setSurname(personaEncontrada.getSurname());
            }
            if(person.getCompanyEmail() != null){
                person.setCompanyEmail(person.getCompanyEmail());
            }else{
                person.setCompanyEmail(personaEncontrada.getCompanyEmail());
            }
            if(person.getPersonalEmail() != null){
                person.setPersonalEmail(person.getPersonalEmail());
            }else{
                person.setPersonalEmail(personaEncontrada.getPersonalEmail());
            }
            if(person.getCity() != null){
                person.setCity(person.getCity());
            }else{
                person.setCity(personaEncontrada.getCity());
            }
            if(person.getActive() != null){
                person.setActive(person.getActive());
            }else{
                person.setActive(personaEncontrada.getActive());
            }
            if(person.getCreatedDate() != null){
                person.setCreatedDate(person.getCreatedDate());
            }else{
                person.setCreatedDate(personaEncontrada.getCreatedDate());
            }
            if(person.getImageUrl() != null){
                person.setImageUrl(person.getImageUrl());
            }else{
                person.setImageUrl(personaEncontrada.getImageUrl());
            }
            if(person.getTerminationDate() != null){
                person.setTerminationDate(person.getTerminationDate());
            }else{
                person.setTerminationDate(personaEncontrada.getTerminationDate());
            }*/
            return  ResponseEntity.ok().body(servicioPersona.addPerson(person));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
