package com.example.jpacascade.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineasFraOutputDto {
    private int idLineas;
    private String proNomb;
    private double cantidad;
    private double precio;
    private Integer cabeceraFraId;
}