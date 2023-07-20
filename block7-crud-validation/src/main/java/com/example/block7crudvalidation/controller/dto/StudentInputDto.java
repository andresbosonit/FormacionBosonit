package com.example.block7crudvalidation.controller.dto;

import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInputDto {
    private int idStudent;
    private int idPersona;
    private int numHoursWeek;
    private String coments;
    private int idProfesor;
    private String branch;
}
