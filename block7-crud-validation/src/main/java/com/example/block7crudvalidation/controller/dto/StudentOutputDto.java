package com.example.block7crudvalidation.controller.dto;

import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentOutputDto {
    private int idStudent;
    private int idPersona;
    private int numHoursWeek;
    private String coments;
    private int idProfesor;
    private String branch;
    public StudentOutputDto(StudentOutputDto studentOutputDto){
        this.idStudent = studentOutputDto.idStudent;
        this.idPersona = studentOutputDto.idPersona;
        this.numHoursWeek = studentOutputDto.numHoursWeek;
        this.coments = studentOutputDto.coments;
        this.idProfesor = studentOutputDto.idProfesor;
        this.branch = studentOutputDto.branch;
    }
}
