package com.example.block7crudvalidation.applicationTest;


import com.example.block7crudvalidation.application.AsignaturaService;
import com.example.block7crudvalidation.application.AsignaturaServiceImpl;
import com.example.block7crudvalidation.controller.dto.AsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.AsignaturaOutputDto;
import com.example.block7crudvalidation.domain.Asignatura;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.UnprocessableEntityException;
import com.example.block7crudvalidation.repository.AsignaturaRepository;
import com.example.block7crudvalidation.repository.StudentRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AsignaturaServiceImplTest {
    @InjectMocks
    private AsignaturaServiceImpl asignaturaService;
    @Mock
    AsignaturaRepository asignaturaRepository;
    @Mock
    StudentRepository studentRepository;
    @Test
    public void addAsignaturaTest(){
        Person person1 = new Person(1,"pepepe","12345667","hola","pepe","fa","fs","f",true,new Date(),"fs",new Date());
        Person person2 = new Person(2,"ururur","12345667","hola","pepe","fa","fs","f",true,new Date(),"fs",new Date());
        Person person3 = new Person(3,"ururur","12345667","hola","pepe","fa","fs","f",true,new Date(),"fs",new Date());
        Profesor profesor = new Profesor(1,person3,"","",new ArrayList<>());

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1,person1,22,"fs",profesor,"",new ArrayList<>()));
        studentList.add(new Student(2,person2,22,"fs",profesor,"",new ArrayList<>()));
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);

        Asignatura asignatura = new Asignatura(1,studentList,"mates","",new Date(),new Date());
        AsignaturaInputDto asignaturaInputDto = new AsignaturaInputDto(new ArrayList<>(),"mates","",new Date(),new Date());
        Mockito.when(asignaturaRepository.save(Mockito.any(Asignatura.class))).thenReturn(asignatura);
        AsignaturaOutputDto asignaturaObtenida = asignaturaService.addAsignatura(asignaturaInputDto);
        Assertions.assertEquals(asignaturaObtenida,asignatura.AsignaturaTOAsignaturaOutputDto());
    }

    @Test
    public void deleteAsignaturaIdTest(){
        Asignatura asignatura = new Asignatura(1,new ArrayList<>(),"mates","",new Date(),new Date());
        Mockito.when(asignaturaRepository.findById(1)).thenReturn(Optional.of(asignatura));
        asignaturaService.deleteAsignaturaId(1);
    }
    @Test
    public void updateAsignaturaTest(){

    }
    @Test
    public void getAllAsignaturaTest(){

    }
    @Test
    public void getAsignaturaTest(){

    }

    @Test
    public void getAsignaturaByIdStudentTest(){

    }
}
