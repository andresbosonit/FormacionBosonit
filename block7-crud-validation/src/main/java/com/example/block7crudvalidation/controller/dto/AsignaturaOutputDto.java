package com.example.block7crudvalidation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignaturaOutputDto {
    private int idAsignatura;
    private List<Integer> students;
    private String asignatura;
    private String comments;
    private Date initialDate;
    private Date finishDate;
}
