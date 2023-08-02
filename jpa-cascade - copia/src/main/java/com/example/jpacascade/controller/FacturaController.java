package com.example.jpacascade.controller;

import com.example.jpacascade.application.FacturaService;
import com.example.jpacascade.controller.dto.FacturaInputDto;
import com.example.jpacascade.controller.dto.FacturaOutputDto;
import com.example.jpacascade.controller.dto.LineasFraInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/factura")
public class FacturaController {
    @Autowired
    FacturaService facturaService;
    @PostMapping
    public ResponseEntity<FacturaOutputDto> añadirFactura(@RequestBody FacturaInputDto facturaInputDto){
        URI location = URI.create("/factura");
        FacturaOutputDto facturaOutputDto = facturaService.addFactura(facturaInputDto);
        return ResponseEntity.created(location).body(facturaOutputDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FacturaOutputDto> getFacturaId(@PathVariable int id){
        FacturaOutputDto facturaOutputDto = facturaService.getFactura(id);
        return ResponseEntity.ok(facturaOutputDto);
    }

    @DeleteMapping
    public ResponseEntity<String> borrarFacturaId(@RequestParam int id){
        facturaService.deleteFactura(id);
        return ResponseEntity.ok().body("La Factura con ID: " + id + " ha sido borrada.");
    }

    @GetMapping()
    public ResponseEntity<List<FacturaOutputDto>> obtenerTodasLasFacturas(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return ResponseEntity.ok().body(facturaService.getAllFacturas(pageNumber,pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaOutputDto> actualizarFactura(@PathVariable Integer id, @RequestBody FacturaInputDto facturaInputDto){
        return  ResponseEntity.ok().body(facturaService.updateFactura(id, facturaInputDto));
    }

    @PostMapping("/linea/{idFra}")
    public ResponseEntity<FacturaOutputDto> añadirLineaAFactura(@PathVariable Integer idFra, @RequestBody LineasFraInputDto lineasFraInputDto){
        return ResponseEntity.ok().body(facturaService.addLinea(idFra,lineasFraInputDto));
    }
}
