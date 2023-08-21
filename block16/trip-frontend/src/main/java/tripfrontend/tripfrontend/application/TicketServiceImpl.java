package tripfrontend.tripfrontend.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tripfrontend.tripfrontend.controller.dtos.input.TicketInput;
import tripfrontend.tripfrontend.controller.dtos.output.TicketOutput;
import tripfrontend.tripfrontend.domain.Cliente;
import tripfrontend.tripfrontend.domain.Ticket;
import tripfrontend.tripfrontend.domain.Viaje;
import tripfrontend.tripfrontend.repository.TicketRepository;

import java.util.HashMap;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    TicketRepository ticketRepository;
    @Override
    public TicketOutput generateTicket(int userId, int viajeId) {
        HashMap<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("userId",userId);
        uriVariables.put("viajeId",viajeId);
        ResponseEntity<Cliente> clienteEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/cliente/{userId}", Cliente.class, uriVariables);
        Cliente cliente = clienteEntity.getBody();
        ResponseEntity<Viaje> viajeEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/viaje/{viajeId}", Viaje.class, uriVariables);
        Viaje viaje = viajeEntity.getBody();
        TicketInput ticketInput = new TicketInput(cliente.getId_cliente(),cliente.getNombre(),cliente.getApellido(),cliente.getEmail(),viaje.getOrigen(),viaje.getDestino(),viaje.getHoraSalida(),viaje.getHoraLlegada());
        return ticketRepository.save(new Ticket(ticketInput)).ticketToTicketOutput();
    }
}
