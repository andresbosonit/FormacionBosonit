package tripfrontend.tripfrontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tripfrontend.tripfrontend.application.TicketService;
import tripfrontend.tripfrontend.controller.dtos.output.TicketOutput;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/generateTicket/{userId}/{viajeId}")
    public ResponseEntity<TicketOutput> generateTicket(@PathVariable int userId, @PathVariable int viajeId) {
        return ResponseEntity.ok().body(ticketService.generateTicket(userId, viajeId));
    }
}
