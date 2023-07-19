package com.example.block7crudvalidation.controller;

import com.example.block7crudvalidation.application.PersonServiceImpl;
import com.example.block7crudvalidation.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.exceptions.UnprocessableEntityException;
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
    public ResponseEntity<PersonOutputDto> añadirPersona(@RequestBody PersonInputDto person){
        URI location = URI.create("/persona");
        PersonOutputDto personOutputDto = servicioPersona.addPerson(person);
        return ResponseEntity.created(location).body(personOutputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDto> obtenerPersonaId(@PathVariable int id) {
        servicioPersona.getPerson(id);
        return ResponseEntity.ok().body(servicioPersona.getPerson(id));
    }

    @DeleteMapping
    public ResponseEntity<String> borrarPersonaId(@RequestParam int id){
        servicioPersona.deletePersonId(id);
        return ResponseEntity.ok().body("La persona con id: "+id+" ha sido eliminada");
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
    public ResponseEntity<PersonOutputDto> actualizarPersona(@RequestBody PersonInputDto person) throws Exception {
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
        person.setTerminationDate(Objects.requireNonNullElse(person.getTerminationDate(), personaEncontrada.getTerminationDate()));
        return  ResponseEntity.ok().body(servicioPersona.updatePerson(person));
    }
}
