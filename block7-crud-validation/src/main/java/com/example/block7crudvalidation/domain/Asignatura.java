package com.example.block7crudvalidation.domain;

import com.example.block7crudvalidation.controller.dto.AsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.AsignaturaOutputDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Asignatura {
    @Id
    @GeneratedValue
    @Column(name = "id_asignatura")
    private int idAsignatura;

    @ManyToMany
    @JoinColumn(name = "id_student")
    private List<Student> students;

    @Column(name = "asignatura")
    private String asignatura;

    @Column(name = "comments")
    private String comments;

    @Column(name = "initial_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date initialDate;

    @Column(name = "finish_date")
    @Temporal(TemporalType.DATE)
    private Date finishDate;

    public Asignatura(AsignaturaInputDto asignaturaInputDto){
        this.asignatura = asignaturaInputDto.getAsignatura();
        this.comments = asignaturaInputDto.getComments();
        this.initialDate = asignaturaInputDto.getInitialDate();
        this.finishDate = asignaturaInputDto.getFinishDate();
    }

    public AsignaturaOutputDto AsignaturaTOAsignaturaOutputDto(){
        List<Integer> list = this.students.stream()
                .map(Student::getIdStudent)
                .collect(Collectors.toList());
        return new AsignaturaOutputDto(this.idAsignatura,list,this.asignatura,this.comments,this.initialDate,this.finishDate);
    }
}