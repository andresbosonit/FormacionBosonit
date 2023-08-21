package tripfrontend.tripfrontend.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tripfrontend.tripfrontend.controller.dtos.input.TicketInput;
import tripfrontend.tripfrontend.controller.dtos.output.TicketOutput;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {
    @Id
    @GeneratedValue
    private int id;
    private int idPasajero;
    private String nombrePasajero;
    private String apellidoPasajero;
    private String emailPasajero;
    private String origenViaje;
    private String destinoViaje;
    private String horaSalida;
    private String horaLlegada;

    public Ticket(TicketInput ticketInput){
        this.idPasajero = ticketInput.getIdPasajero();
        this.nombrePasajero = ticketInput.getNombrePasajero();
        this.apellidoPasajero = ticketInput.getApellidoPasajero();
        this.emailPasajero = ticketInput.getEmailPasajero();
        this.origenViaje = ticketInput.getOrigenViaje();
        this.destinoViaje = ticketInput.getDestinoViaje();
        this.horaSalida = ticketInput.getHoraSalida();
        this.horaLlegada = ticketInput.getHoraLlegada();
    }

    public TicketOutput ticketToTicketOutput(){
        return new TicketOutput(this.id,this.idPasajero,this.nombrePasajero,this.apellidoPasajero,this.emailPasajero,this.origenViaje,this.destinoViaje,this.horaSalida,this.horaLlegada);
    }
}
