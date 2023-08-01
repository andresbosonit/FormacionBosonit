package com.example.jpacascade.controller;

import com.example.jpacascade.application.CabeceraFraService;
import com.example.jpacascade.controller.dto.CabeceraFraInputDto;
import com.example.jpacascade.controller.dto.CabeceraFraOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cabecera")
public class CabeceraFraController {
    @Autowired
    CabeceraFraService cabeceraFraService;
    @PostMapping
    public ResponseEntity<CabeceraFraOutputDto> añadirCabecera(@RequestBody CabeceraFraInputDto cabeceraFraInputDto){
        URI location = URI.create("/cabecera");
        CabeceraFraOutputDto cabeceraFraOutputDto = cabeceraFraService.addCabecera(cabeceraFraInputDto);
        return ResponseEntity.created(location).body(cabeceraFraOutputDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CabeceraFraOutputDto> getCabeceraId(@PathVariable int id){
        CabeceraFraOutputDto cabeceraFraOutputDto = cabeceraFraService.getCabecera(id);
        return ResponseEntity.ok(cabeceraFraOutputDto);
    }

    @DeleteMapping
    public ResponseEntity<String> borrarCabeceraId(@RequestParam int id){
        cabeceraFraService.deleteCabecera(id);
        return ResponseEntity.ok().body("La cabecera con ID: " + id + " ha sido borrada.");
    }

    @GetMapping()
    public ResponseEntity<List<CabeceraFraOutputDto>> obtenerTodasLasCabeceras(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return ResponseEntity.ok().body(cabeceraFraService.getAllCabeceras(pageNumber,pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CabeceraFraOutputDto> actualizarCabecera(@PathVariable Integer id, @RequestBody CabeceraFraInputDto cabeceraFraInputDto){
        return  ResponseEntity.ok().body(cabeceraFraService.updateCabecera(id, cabeceraFraInputDto));
    }
}
