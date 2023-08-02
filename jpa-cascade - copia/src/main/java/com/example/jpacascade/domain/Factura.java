package com.example.jpacascade.domain;

import com.example.jpacascade.controller.dto.FacturaInputDto;
import com.example.jpacascade.controller.dto.FacturaOutputDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Factura {
    @Id
    @GeneratedValue
    private int idFactura;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<LineasFra> lineasFraList;
    private double importeFra;

    public Factura(FacturaInputDto facturaInputDto){
        this.importeFra = facturaInputDto.getImporteFra();
    }

    public FacturaOutputDto facturaToFacturaOutputDto(){
        return new FacturaOutputDto(
                this.idFactura,
                this.cliente.clienteToClienteOutputDto(),
                this.lineasFraList.stream().map(LineasFra::lineasFraToLineasFraOutputDto).collect(Collectors.toList()),
                this.importeFra
        );

    }
}
