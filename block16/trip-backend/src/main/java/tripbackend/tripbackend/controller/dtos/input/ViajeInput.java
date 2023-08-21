package tripbackend.tripbackend.controller.dtos.input;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tripbackend.tripbackend.domain.Cliente;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViajeInput {
    private String origen;
    private String destino;
    private String horaSalida;
    private String horaLlegada;
    private String estado;
    private List<Integer> listaPasajeros;
}
