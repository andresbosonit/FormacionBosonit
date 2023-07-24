package com.example.block7crudvalidation.controller.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonStudentOutputFullDto extends PersonOutputDto{
    private StudentOutputDto studentOutputDto;
    public PersonStudentOutputFullDto(PersonOutputDto personOutputDto, StudentOutputDto studentOutputDto){
        super(personOutputDto);
        this.studentOutputDto = studentOutputDto;
    }

}
