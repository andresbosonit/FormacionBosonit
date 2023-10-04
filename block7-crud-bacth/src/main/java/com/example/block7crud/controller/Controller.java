package com.example.block7crud.controller;

import com.example.block7crud.application.ServicioPersonaImpl;
import com.example.block7crud.controller.dto.PersonInputDto;
import com.example.block7crud.controller.dto.PersonOutputDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/persona")
public class Controller {

    @Autowired
    ServicioPersonaImpl servicioPersona;

    @PostMapping
    public ResponseEntity<PersonOutputDto> añadirPersona(@RequestBody PersonInputDto person) {
        URI location = URI.create("/persona");
        return ResponseEntity.created(location).body(servicioPersona.añadirPersona(person));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPersonaId(@PathVariable int id) {
        PersonOutputDto personOutputDto = servicioPersona.consultarPersonaId(id);
        if(personOutputDto == null){
            return new ResponseEntity<>("No se ha encontrado a la persona con id: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(personOutputDto);
    }

    @DeleteMapping
    public ResponseEntity<String> borrarPersonaId(@RequestParam int id) {
        try {
            servicioPersona.borrarPesona(id);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se ha encontrado a la persona con id: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body("Se ha borrado la persona con id: " + id);
    }

    @GetMapping("/nombre/{nombre}")
    public Iterable<PersonOutputDto> obtenerPersonasNombre(@PathVariable String nombre,
                                                           @RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                           @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return servicioPersona.consultarPersonaNombre(nombre,pageNumber,pageSize);
    }
    @GetMapping
    public Iterable<PersonOutputDto> obtenerTodasLasPersonas(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return servicioPersona.consultarTodosLosRegistros(pageNumber, pageSize);
    }

    @PutMapping
    public ResponseEntity<?> actualizarPersona(@RequestBody PersonInputDto person) {
        PersonOutputDto personOutputDto = servicioPersona.modificarPersona(person);
        if(personOutputDto == null){
            return new ResponseEntity<>("No se ha encontrado a la persona con id: " + person.getId(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(personOutputDto);
    }

    @GetMapping("/hola")
    public int saludar(
            @RequestParam String nombreJugador1,
            HttpServletRequest request) {

        String ipCliente1 = request.getRemoteAddr();
        System.out.println("Hola, " + nombreJugador1 + ". Tu IP es: " + ipCliente1);
        return 123234;
    }
}
