package com.example.jpacascade.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabeceraFraInputDto {
    private Integer clienteId;
    private List<Integer> lineasFraIdList;
    private double importeFra = 0;
}
