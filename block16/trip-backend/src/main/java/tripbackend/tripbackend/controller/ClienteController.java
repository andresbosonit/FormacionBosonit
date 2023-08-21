package tripbackend.tripbackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripbackend.tripbackend.application.ClienteService;
import tripbackend.tripbackend.controller.dtos.input.ClienteInput;
import tripbackend.tripbackend.controller.dtos.output.ClienteOutput;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteOutput> addCliente(@RequestBody ClienteInput clienteInput){
        ClienteOutput clienteOutput = clienteService.addCliente(clienteInput);
        return ResponseEntity.ok().body(clienteOutput);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteOutput> getCliente(@PathVariable int id) {
        ClienteOutput clienteOutput = clienteService.getCliente(id);
        return ResponseEntity.ok().body(clienteOutput);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCliente(@RequestParam int id){
        clienteService.deleteCliente(id);
        return ResponseEntity.ok().body("El cliente con id: "+id+" ha sido eliminado");
    }
    @GetMapping
    public Iterable<ClienteOutput> getAllCliente(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return clienteService.getAllCliente(pageNumber, pageSize);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteOutput> updateCliente(@PathVariable Integer id, @RequestBody ClienteInput clienteInput){
        return ResponseEntity.ok().body(clienteService.updateCliente(id, clienteInput));
    }

    // Otros metodos

    @GetMapping("/count/{idViaje}")
    public ResponseEntity<Integer> countPasajeros(@PathVariable Integer idViaje){
        return ResponseEntity.ok().body(clienteService.countPasajeros(idViaje));
    }
}
