package tripbackend.tripbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tripbackend.tripbackend.controller.dtos.input.ViajeInput;
import tripbackend.tripbackend.controller.dtos.output.ClienteOutput;
import tripbackend.tripbackend.controller.dtos.output.ViajeOutput;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="viaje")
public class Viaje {
    @Id
    @GeneratedValue
    private int id_viaje;
    private String origen;
    private String destino;
    private String horaSalida;
    private String horaLlegada;
    private String estado;
    @ManyToMany(mappedBy = "listaViajes")
    private List<Cliente> listaPasajeros;

    public Viaje(ViajeInput viajeInput){
        this.origen = viajeInput.getOrigen();
        this.destino = viajeInput.getDestino();
        this.horaSalida = viajeInput.getHoraSalida();
        this.horaLlegada = viajeInput.getHoraLlegada();
        this.estado = viajeInput.getEstado();
    }

    public ViajeOutput viajeToViajeOutput(){
        List<Integer> list = this.listaPasajeros.stream().map(Cliente -> Cliente.getId_cliente()).toList();
        return new ViajeOutput(this.id_viaje,this.origen,this.destino,this.horaSalida,this.horaLlegada,this.estado,list);
    }
}
