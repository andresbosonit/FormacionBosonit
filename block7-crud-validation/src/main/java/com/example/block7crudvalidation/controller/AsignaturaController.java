package com.example.block7crudvalidation.controller;

import com.example.block7crudvalidation.application.AsignaturaService;
import com.example.block7crudvalidation.controller.dto.AsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.AsignaturaOutputDto;
import com.example.block7crudvalidation.domain.Asignatura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/asignatura")
public class AsignaturaController {
    @Autowired
    AsignaturaService asignaturaService;

    @PostMapping
    public ResponseEntity<AsignaturaOutputDto> añadirAsignatura(@RequestBody AsignaturaInputDto Asignatura){
        URI location = URI.create("/Asignatura");
        AsignaturaOutputDto asignaturaOutputDto = asignaturaService.addAsignatura(Asignatura);
        return ResponseEntity.created(location).body(asignaturaOutputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaOutputDto> obtenerAsignaturaId(@PathVariable int id) {
        asignaturaService.getAsignatura(id);
        return ResponseEntity.ok().body(asignaturaService.getAsignatura(id));
    }

    @DeleteMapping
    public ResponseEntity<String> borrarAsignaturaId(@RequestParam int id){
        asignaturaService.deleteAsignaturaId(id);
        return ResponseEntity.ok().body("El Asignatura con id: "+id+" ha sido eliminada");
    }
    @GetMapping
    public Iterable<AsignaturaOutputDto> obtenerTodosLosAsignatura(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return asignaturaService.getAllAsignatura(pageNumber, pageSize);
    }

    @PutMapping
    public ResponseEntity<AsignaturaOutputDto> actualizarAsignatura(@RequestBody AsignaturaInputDto Asignatura){
        return  ResponseEntity.ok().body(asignaturaService.updateAsignatura(Asignatura));
    }

    @GetMapping("/idEstudiante/{id}")
    public ResponseEntity<String> obtenerAsignaturasId(@PathVariable int id) {
        List<AsignaturaOutputDto> asignaturas = asignaturaService.getAsignaturaByIdStudent(id);
        String mensaje = "El  con id: " + id + " esta en las siguientes asignaturas:\n";
        for(AsignaturaOutputDto asignatura : asignaturas){
            mensaje += "Id de la asignatura: " + asignatura.getIdAsignatura() + " Nombre de la asignatura: " + asignatura.getAsignatura() + "\n";
        }
        return ResponseEntity.ok().body(mensaje);
    }
}
