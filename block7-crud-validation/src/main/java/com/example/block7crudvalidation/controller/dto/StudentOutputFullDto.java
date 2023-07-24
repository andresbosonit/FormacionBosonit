package com.example.block7crudvalidation.controller.dto;

import com.example.block7crudvalidation.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data
public class StudentOutputFullDto extends StudentOutputDto{
    private PersonOutputDto personOutputDto;
    public StudentOutputFullDto(StudentOutputDto studentOutputDto, PersonOutputDto personOutputDto){
        super(studentOutputDto);
        this.personOutputDto = personOutputDto;
    }
}
