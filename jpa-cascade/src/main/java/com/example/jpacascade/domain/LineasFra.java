package com.example.jpacascade.domain;
import com.example.jpacascade.controller.dto.ClienteInputDto;
import com.example.jpacascade.controller.dto.ClienteOutputDto;
import com.example.jpacascade.controller.dto.LineasFraInputDto;
import com.example.jpacascade.controller.dto.LineasFraOutputDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LineasFra {
    @Id
    @GeneratedValue
    private int idLineas;
    @Column(nullable = false)
    private String proNomb;
    private double cantidad;
    private double precio;
    @ManyToOne
    @JoinColumn(name = "id_cabecera_fra")
    private CabeceraFra cabeceraFra;

    public LineasFra(LineasFraInputDto lineasFraInputDto){
        this.proNomb = lineasFraInputDto.getProNomb();
        this.cantidad = lineasFraInputDto.getCantidad();
        this.precio = lineasFraInputDto.getPrecio();
    }

    public LineasFraOutputDto lineasFraToLineasFraOutputDto(){
        return new LineasFraOutputDto(this.idLineas,this.proNomb,this.cantidad,this.precio,this.cabeceraFra.getIdCabeceraFra());
    }
}
