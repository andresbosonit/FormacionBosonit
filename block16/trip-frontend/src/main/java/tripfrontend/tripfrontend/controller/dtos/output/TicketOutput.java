package tripfrontend.tripfrontend.controller.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketOutput {
    private int id;
    private int idPasajero;
    private String nombrePasajero;
    private String apellidoPasajero;
    private String emailPasajero;
    private String origenViaje;
    private String destinoViaje;
    private String horaSalida;
    private String horaLlegada;
}
