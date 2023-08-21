package tripfrontend.tripfrontend.controller.dtos.input;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteInput {
    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;
    private String telefono;
    private List<Integer> listaViajes;
}
