package com.example.jpacascade.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabeceraFraOutputDto {
    private int idCabeceraFra;
    private ClienteOutputDto clienteId;
    private List<LineasFraOutputDto> lineasFraIdList;
    private double importeFra = 0;
}
