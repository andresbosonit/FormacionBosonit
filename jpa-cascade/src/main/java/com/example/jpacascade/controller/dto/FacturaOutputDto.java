package com.example.jpacascade.controller.dto;

import com.example.jpacascade.domain.Cliente;
import com.example.jpacascade.domain.LineasFra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaOutputDto {
    private int idCabeceraFra;
    private ClienteOutputDto cliente;
    private List<LineasFraOutputDto> lineasFraList;
    private double importeFra = 0;
}
