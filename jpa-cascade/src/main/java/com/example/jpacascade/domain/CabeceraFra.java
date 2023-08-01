package com.example.jpacascade.domain;

import com.example.jpacascade.controller.dto.CabeceraFraInputDto;
import com.example.jpacascade.controller.dto.CabeceraFraOutputDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CabeceraFra {
    @Id
    @GeneratedValue
    private int idCabeceraFra;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @OneToMany(mappedBy = "cabeceraFra")
    private List<LineasFra> lineasFraList = new ArrayList<>();
    private double importeFra;

    public CabeceraFra(CabeceraFraInputDto cabeceraFraInputDto){
        this.importeFra = cabeceraFraInputDto.getImporteFra();
    }

    public CabeceraFraOutputDto cabeceraFraToCabeceraFraOutputDto(){
        List<Integer> list = this.lineasFraList.stream()
                .map(LineasFra::getIdLineas)
                .collect(Collectors.toList());
        return new CabeceraFraOutputDto(this.idCabeceraFra,this.cliente.getIdCliente(),list,this.importeFra);
    }
}
