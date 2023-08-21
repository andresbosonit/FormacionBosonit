package tripfrontend.tripfrontend.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tripfrontend.tripfrontend.controller.dtos.input.ClienteInput;
import tripfrontend.tripfrontend.controller.dtos.output.ClienteOutput;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private int id_cliente;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;
    private String telefono;
    private List<Viaje> listaViajes;

    public Cliente(ClienteInput clienteInput){
        this.nombre = clienteInput.getNombre();
        this.apellido = clienteInput.getApellido();
        this.edad = clienteInput.getEdad();
        this.email = clienteInput.getEmail();
        this.telefono = clienteInput.getTelefono();
    }

    public ClienteOutput clienteToClienteOutput(){
        List<Integer> list = this.listaViajes.stream().map(Viaje -> Viaje.getId_viaje()).toList();
        return new ClienteOutput(this.id_cliente,this.nombre,this.apellido,this.edad,this.email,this.telefono,list);
    }
}
