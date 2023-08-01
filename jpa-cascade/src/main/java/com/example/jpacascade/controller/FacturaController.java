package com.example.jpacascade.controller;

import com.example.jpacascade.application.FacturaService;
import com.example.jpacascade.controller.dto.ClienteOutputDto;
import com.example.jpacascade.controller.dto.FacturaOutputDto;
import com.example.jpacascade.controller.dto.LineasFraInputDto;
import com.example.jpacascade.controller.dto.LineasFraOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factura")
public class FacturaController {
    @Autowired
    FacturaService facturaService;
    @GetMapping
    public ResponseEntity<List<FacturaOutputDto>> obtenerTodosLosCliente(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return ResponseEntity.ok().body(facturaService.getAllFacturas(pageNumber,pageSize));
    }

    @DeleteMapping
    public ResponseEntity<String> borrarCabeceraId(@RequestParam int id){
        facturaService.deleteFactura(id);
        return ResponseEntity.ok().body("La factura con ID: " + id + " ha sido borrada.");
    }

    @PostMapping("/linea/{idFra}")
    public ResponseEntity<FacturaOutputDto> añadirLinea(@PathVariable Integer idFra, @RequestBody LineasFraInputDto lineasFraInputDto){
        return ResponseEntity.ok().body(facturaService.addLinea(idFra, lineasFraInputDto));
    }
}
