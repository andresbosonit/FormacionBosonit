package com.example.jpacascade.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaOutputDto{
    private int id;
    double importeFra = 0;
    private ClienteOutputDto clienteOutputDto;
    private List<LineasFraOutputDto> lineasFraOutputDtoList;
    public FacturaOutputDto(CabeceraFraOutputDto cabeceraFraOutputDto, ClienteOutputDto clienteOutputDto, List<LineasFraOutputDto> lineasFraOutputDtoList){
        this.id = cabeceraFraOutputDto.getIdCabeceraFra();
        this.importeFra = cabeceraFraOutputDto.getImporteFra();
        this.clienteOutputDto = clienteOutputDto;
        this.lineasFraOutputDtoList = lineasFraOutputDtoList;
    }
}
