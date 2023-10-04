package com.example.block7crud.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonOutputDto implements Serializable {
    private int id;
    String nombre;
    String edad;
    String poblacion;
}
