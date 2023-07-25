package com.example.block7crudvalidation.controller.dto;

import com.example.block7crudvalidation.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorOutputDto {
    private int idProfesor;
    private Integer idPersona;
    private String comments;
    private String branch;
}
