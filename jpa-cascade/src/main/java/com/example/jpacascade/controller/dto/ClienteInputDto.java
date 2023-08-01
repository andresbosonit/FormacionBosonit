package com.example.jpacascade.controller.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteInputDto {
    private String nombre;
    private List<Integer> facturaIdList;

    public ClienteInputDto(String nombre) {
        this.nombre = nombre;
    }
}
