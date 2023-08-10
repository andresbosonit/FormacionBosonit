package com.example.block7crudvalidation.entities;

import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.controller.dto.StudentOutputDto;
import com.example.block7crudvalidation.controller.dto.StudentOutputFullDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StudentOutputDtoTest {
    @InjectMocks
    StudentOutputDto studentOutputDto;

    public StudentOutputDtoTest() {
        studentOutputDto = new StudentOutputDto(1,1,12,"",1,"");
    }

    @Test
    public void ConstructorOutputTest(){
        StudentOutputDto studentOutputDto1 = new StudentOutputDto(studentOutputDto);
        Assertions.assertEquals(studentOutputDto,studentOutputDto1);
    }

    @Test
    public void StudentOutputFullDto(){
        PersonOutputDto personOutputDto = new PersonOutputDto();
        StudentOutputFullDto studentOutputFullDto = new StudentOutputFullDto(studentOutputDto,personOutputDto);
        Assertions.assertEquals(studentOutputFullDto.getPersonOutputDto(),personOutputDto);
    }
}
