package tripbackend.tripbackend.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tripbackend.tripbackend.controller.dtos.input.ClienteInput;
import tripbackend.tripbackend.controller.dtos.output.ClienteOutput;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue
    private int id_cliente;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;
    private String telefono;
    @ManyToMany
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
