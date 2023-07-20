package com.example.block7crudvalidation.controller;

import com.example.block7crudvalidation.application.EstudianteAsignaturaServiceImpl;
import com.example.block7crudvalidation.controller.dto.EstudianteAsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.EstudianteAsignaturaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/estudianteAsignatura")
public class EstudianteAsignaturaController {
    @Autowired
    EstudianteAsignaturaServiceImpl estudianteAsignaturaService;

    @PostMapping
    public ResponseEntity<EstudianteAsignaturaOutputDto> añadirEstudianteAsignatura(@RequestBody EstudianteAsignaturaInputDto estudianteAsignatura){
        URI location = URI.create("/estudianteAsignatura");
        EstudianteAsignaturaOutputDto estudianteAsignaturaOutputDto = estudianteAsignaturaService.addEstudianteAsignatura(estudianteAsignatura);
        return ResponseEntity.created(location).body(estudianteAsignaturaOutputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteAsignaturaOutputDto> obtenerEstudianteAsignaturaId(@PathVariable int id) {
        estudianteAsignaturaService.getEstudianteAsignatura(id);
        return ResponseEntity.ok().body(estudianteAsignaturaService.getEstudianteAsignatura(id));
    }

    @DeleteMapping
    public ResponseEntity<String> borrarEstudianteAsignaturaId(@RequestParam int id){
        estudianteAsignaturaService.deleteEstudianteAsignaturaId(id);
        return ResponseEntity.ok().body("El EstudianteAsignatura con id: "+id+" ha sido eliminada");
    }
    @GetMapping
    public Iterable<EstudianteAsignaturaOutputDto> obtenerTodosLosEstudianteAsignatura(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return estudianteAsignaturaService.getAllEstudianteAsignatura(pageNumber, pageSize);
    }

    @PutMapping
    public ResponseEntity<EstudianteAsignaturaOutputDto> actualizarEstudianteAsignatura(@RequestBody EstudianteAsignaturaInputDto estudianteAsignatura){
        return  ResponseEntity.ok().body(estudianteAsignaturaService.updateEstudianteAsignatura(estudianteAsignatura));
    }
}
