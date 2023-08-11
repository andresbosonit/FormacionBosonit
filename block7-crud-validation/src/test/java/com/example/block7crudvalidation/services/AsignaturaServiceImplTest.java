package com.example.block7crudvalidation.services;


import com.example.block7crudvalidation.application.AsignaturaService;
import com.example.block7crudvalidation.application.AsignaturaServiceImpl;
import com.example.block7crudvalidation.controller.dto.AsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.AsignaturaOutputDto;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.domain.Asignatura;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.UnprocessableEntityException;
import com.example.block7crudvalidation.repository.AsignaturaRepository;
import com.example.block7crudvalidation.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AsignaturaServiceImplTest {
    @InjectMocks
    private AsignaturaServiceImpl asignaturaService;
    @Mock
    AsignaturaRepository asignaturaRepository;
    @Mock
    StudentRepository studentRepository;
    @Test
    public void addAsignaturaTest(){
        Person person1 = new Person(1,"pepepe","12345667",true,"hola","pepe","fa","fs","f",true,new Date(),"fs",new Date());
        Person person2 = new Person(2,"ururur","12345667",true,"hola","pepe","fa","fs","f",true,new Date(),"fs",new Date());
        Person person3 = new Person(3,"ururur","12345667",true,"hola","pepe","fa","fs","f",true,new Date(),"fs",new Date());
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
        Asignatura asignatura = new Asignatura(1,new ArrayList<>(),"mates","",new Date(),new Date());
        AsignaturaInputDto asignaturaInputDto = new AsignaturaInputDto(new ArrayList<>(),"mates","",new Date(),new Date());
        Mockito.when(asignaturaRepository.findById(1)).thenReturn(Optional.of(asignatura));
        Mockito.when(asignaturaRepository.save(asignatura)).thenReturn(asignatura);
        asignaturaService.updateAsignatura(1,asignaturaInputDto);
    }
    @Test
    public void getAllAsignaturaTest(){
        Asignatura asignatura = new Asignatura(1,new ArrayList<>(),"mates","",new Date(),new Date());
        List<Asignatura> listEntrada = new ArrayList<>();
        listEntrada.add(asignatura);
        Page<Asignatura> personPage = new PageImpl<>(listEntrada);
        PageRequest pageRequest = PageRequest.of(1, 1);
        Mockito.when(asignaturaRepository.findAll(pageRequest)).thenReturn(personPage);
        List<AsignaturaOutputDto> listObtenida = asignaturaService.getAllAsignatura(1,1);
        List<AsignaturaOutputDto> listEsperada= listEntrada.stream().map(asignatura1 -> asignatura1.AsignaturaTOAsignaturaOutputDto()).collect(Collectors.toList());
        Assertions.assertEquals(listEsperada,listObtenida);
    }
    @Test
    public void getAsignaturaTest(){

    }

    @Test
    public void getAsignaturaByIdStudentTest(){

    }
}
