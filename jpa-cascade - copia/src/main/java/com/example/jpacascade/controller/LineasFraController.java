package com.example.jpacascade.controller;

import com.example.jpacascade.application.LineasFraService;
import com.example.jpacascade.controller.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/linea")
public class LineasFraController {
    @Autowired
    LineasFraService lineasFraService;
    @PostMapping
    public ResponseEntity<LineasFraOutputDto> añadirLinea(@RequestBody LineasFraInputDto lineasFraInputDto){
        URI location = URI.create("/linea");
        LineasFraOutputDto lineasFraOutputDto = lineasFraService.addLinea(lineasFraInputDto);
        return ResponseEntity.created(location).body(lineasFraOutputDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LineasFraOutputDto> getLineaId(@PathVariable int id){
        LineasFraOutputDto lineasFraOutputDto = lineasFraService.getLinea(id);
        return ResponseEntity.ok(lineasFraOutputDto);
    }

    @DeleteMapping
    public ResponseEntity<String> borrarLineaId(@RequestParam int id){
        lineasFraService.deleteLinea(id);
        return ResponseEntity.ok().body("La linea con ID: " + id + " ha sido borrada.");
    }

    @GetMapping()
    public ResponseEntity<List<LineasFraOutputDto>> obtenerTodasLasLineas(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return ResponseEntity.ok().body(lineasFraService.getAllLinea(pageNumber,pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineasFraOutputDto> actualizarLinea(@PathVariable Integer id, @RequestBody LineasFraInputDto lineasFraInputDto){
        return  ResponseEntity.ok().body(lineasFraService.updateLinea(id, lineasFraInputDto));
    }
}
