package com.example.block7crudvalidation.domain;

import com.example.block7crudvalidation.controller.dto.ProfesorInputDto;
import com.example.block7crudvalidation.controller.dto.ProfesorOutputDto;
import com.example.block7crudvalidation.repository.ProfesorRepository;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profesor {
    @Id
    @GeneratedValue
    @Column(name = "id_profesor")
    private int idProfesor;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Person persona;

    @Column(name = "comments")
    private String comments;

    @Column(name = "branch", nullable = false)
    private String branch;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesor")
    private List<Student> students;

    public Profesor(ProfesorInputDto profesor){
        this.comments = profesor.getComments();
        this.branch = profesor.getBranch();
    }

    public ProfesorOutputDto profesorToProfesorOutputDto(){
        return new ProfesorOutputDto(this.idProfesor,this.persona.getIdPersona(),this.comments,this.branch);
    }
}
