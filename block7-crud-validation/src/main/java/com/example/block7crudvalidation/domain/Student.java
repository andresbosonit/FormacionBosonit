package com.example.block7crudvalidation.domain;

import com.example.block7crudvalidation.controller.dto.StudentInputDto;
import com.example.block7crudvalidation.controller.dto.StudentOutputDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue
    @Column(name = "id_student")
    private int idStudent;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Person persona;

    @Column(name = "num_hours_week", nullable = false)
    private int numHoursWeek;

    @Column(name = "coments")
    private String coments;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;

    @Column(name = "branch", nullable = false)
    private String branch;

    @ManyToMany
    @JoinColumn(name = "id_asignatura")
    private List<Asignatura> asignaturas;
    public Student(StudentInputDto studentInputDto){
        this.numHoursWeek = studentInputDto.getNumHoursWeek();
        this.coments = studentInputDto.getComents();
        this.branch = studentInputDto.getBranch();
    }

    public StudentOutputDto studentToStudentOutputDto(){
        return new StudentOutputDto(this.idStudent,this.persona.getIdPersona(),this.numHoursWeek,this.coments,this.profesor.getIdProfesor(),this.branch);
    }
}
