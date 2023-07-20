package com.example.block7crudvalidation.controller.dto;

import com.example.block7crudvalidation.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteAsignaturaOutputDto {
    private int idAsignatura;
    private int idStudent;
    private String asignatura;
    private String comments;
    private Date initialDate;
    private Date finishDate;
}
