package com.example.block7crudvalidation.controller.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonProfesorOutputFullDto extends PersonOutputDto{
    private ProfesorOutputDto profesorOutputDto;
    public PersonProfesorOutputFullDto(PersonOutputDto personOutputDto, ProfesorOutputDto profesorOutputDto){
        super(personOutputDto);
        this.profesorOutputDto = profesorOutputDto;
    }
}
