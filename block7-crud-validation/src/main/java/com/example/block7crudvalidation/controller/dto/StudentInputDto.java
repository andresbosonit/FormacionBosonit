package com.example.block7crudvalidation.controller.dto;

import com.example.block7crudvalidation.domain.Asignatura;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInputDto {
    private Integer idPersona;
    private Integer numHoursWeek;
    private String coments;
    private Integer idProfesor;
    private String branch;
}
