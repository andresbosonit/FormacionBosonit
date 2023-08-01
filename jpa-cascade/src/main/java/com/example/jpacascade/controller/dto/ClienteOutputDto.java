package com.example.jpacascade.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteOutputDto {
    private int idCliente;
    private String nombre;
    private List<Integer> facturaIdList;
}
