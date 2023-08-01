package com.example.jpacascade.controller.dto;

import com.example.jpacascade.domain.CabeceraFra;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteInputDto {
    private String nombre;
    private List<Integer> cabeceraFraIdList;
}
