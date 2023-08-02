package com.example.jpacascade.domain;

import com.example.jpacascade.controller.dto.ClienteInputDto;
import com.example.jpacascade.controller.dto.ClienteOutputDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue
    private int idCliente;
    @Column(nullable = false, length = 100)
    private String nombre;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Factura> facturaList;

    public Cliente(ClienteInputDto clienteInputDto){
        this.nombre = clienteInputDto.getNombre();
    }

    public ClienteOutputDto clienteToClienteOutputDto(){
        List<Integer> list = this.facturaList.stream()
                .map(Factura::getIdFactura)
                .collect(Collectors.toList());
        return new ClienteOutputDto(this.idCliente,this.nombre,list);
    }
}
