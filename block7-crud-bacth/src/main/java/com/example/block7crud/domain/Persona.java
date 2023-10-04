package com.example.block7crud.domain;

import com.example.block7crud.controller.dto.PersonInputDto;
import com.example.block7crud.controller.dto.PersonOutputDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String edad;
    private String poblacion;

    public Persona(PersonInputDto personInputDto){
        this.id = personInputDto.getId();
        this.nombre = personInputDto.getNombre();
        this.edad = personInputDto.getEdad();
        this.poblacion = personInputDto.getPoblacion();
    }

    public PersonOutputDto personaToPersonaOutputDto(){
        return new PersonOutputDto(this.id, this.nombre, this.edad, this.poblacion);
    }
}
