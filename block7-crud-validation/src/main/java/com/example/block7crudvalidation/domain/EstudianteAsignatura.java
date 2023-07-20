package com.example.block7crudvalidation.domain;

import com.example.block7crudvalidation.controller.dto.EstudianteAsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.EstudianteAsignaturaOutputDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteAsignatura {
    @Id
    @GeneratedValue
    @Column(name = "id_asignatura")
    private int idAsignatura;

    @OneToOne
    @JoinColumn(name = "id_student")
    private Student idStudent;

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

    public EstudianteAsignatura(EstudianteAsignaturaInputDto estudianteAsignaturaInputDto){
        this.idAsignatura = estudianteAsignaturaInputDto.getIdAsignatura();
        this.asignatura = estudianteAsignaturaInputDto.getAsignatura();
        this.comments = estudianteAsignaturaInputDto.getComments();
        this.initialDate = estudianteAsignaturaInputDto.getInitialDate();
        this.finishDate = estudianteAsignaturaInputDto.getFinishDate();
    }

    public EstudianteAsignaturaOutputDto estudianteAsignaturaTOEstudianteAsignaturaOutputDto(){
        return new EstudianteAsignaturaOutputDto(this.idAsignatura,this.idStudent.getIdStudent(),this.asignatura,this.comments,this.initialDate,this.finishDate);
    }
}
