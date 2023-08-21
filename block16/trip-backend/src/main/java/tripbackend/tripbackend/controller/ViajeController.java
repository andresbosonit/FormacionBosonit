package tripbackend.tripbackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripbackend.tripbackend.application.ViajeService;
import tripbackend.tripbackend.controller.dtos.input.ViajeInput;
import tripbackend.tripbackend.controller.dtos.output.ViajeOutput;

@RestController
@RequestMapping("/viaje")
public class ViajeController {
    @Autowired
    ViajeService viajeService;

    @PostMapping
    public ResponseEntity<ViajeOutput> addviaje(@RequestBody ViajeInput viajeInput){
        ViajeOutput viajeOutput = viajeService.addViaje(viajeInput);
        return ResponseEntity.ok().body(viajeOutput);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViajeOutput> getviaje(@PathVariable int id) {
        ViajeOutput viajeOutput = viajeService.getViaje(id);
        return ResponseEntity.ok().body(viajeOutput);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteviaje(@RequestParam int id){
        viajeService.deleteViaje(id);
        return ResponseEntity.ok().body("El viaje con id: "+id+" ha sido eliminado");
    }
    @GetMapping
    public Iterable<ViajeOutput> getAllviaje(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return viajeService.getAllViaje(pageNumber, pageSize);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViajeOutput> updateviaje(@PathVariable Integer id, @RequestBody ViajeInput viajeInput){
        return ResponseEntity.ok().body(viajeService.updateViaje(id, viajeInput));
    }

    // Otros metodos

    @PostMapping("/addPasajero/{idViaje}/{idPasajero}")
    public ResponseEntity<ViajeOutput> addPasajero(@PathVariable Integer idViaje, @PathVariable Integer idPasajero){
        return ResponseEntity.ok().body(viajeService.addPasajero(idViaje,idPasajero));
    }

    @PutMapping("/{idViaje}/{estado}")
    public ResponseEntity<ViajeOutput> modifyStatus(@PathVariable Integer idViaje, @PathVariable String estado){
        return ResponseEntity.ok().body(viajeService.modifyEstado(idViaje,estado));
    }

    @GetMapping("/verify/{idViaje}")
    public ResponseEntity<String> verifyViaje(@PathVariable Integer idViaje){
        return ResponseEntity.ok().body(viajeService.verifyViaje(idViaje));
    }
}
