package com.example.jpacascade.controller;

import com.example.jpacascade.application.CabeceraFraService;
import com.example.jpacascade.application.ClienteService;
import com.example.jpacascade.controller.dto.CabeceraFraInputDto;
import com.example.jpacascade.controller.dto.CabeceraFraOutputDto;
import com.example.jpacascade.controller.dto.ClienteInputDto;
import com.example.jpacascade.controller.dto.ClienteOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;
    @PostMapping
    public ResponseEntity<ClienteOutputDto> añadirCliente(@RequestBody ClienteInputDto clienteInputDto){
        URI location = URI.create("/cliente");
        ClienteOutputDto clienteOutputDto = clienteService.addCliente(clienteInputDto);
        return ResponseEntity.created(location).body(clienteOutputDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClienteOutputDto> getClienteId(@PathVariable int id){
        ClienteOutputDto clienteOutputDto = clienteService.getCliente(id);
        return ResponseEntity.ok(clienteOutputDto);
    }

    @DeleteMapping
    public ResponseEntity<String> borrarClienteId(@RequestParam int id){
        clienteService.deleteCliente(id);
        return ResponseEntity.ok().body("El cliente con ID: " + id + " ha sido borrado.");
    }

    @GetMapping()
    public ResponseEntity<List<ClienteOutputDto>> obtenerTodosLosCliente(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return ResponseEntity.ok().body(clienteService.getAllCliente(pageNumber,pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteOutputDto> actualizarCliente(@PathVariable Integer id, @RequestBody ClienteInputDto clienteInputDto){
        return  ResponseEntity.ok().body(clienteService.updateCliente(id, clienteInputDto));
    }
}
