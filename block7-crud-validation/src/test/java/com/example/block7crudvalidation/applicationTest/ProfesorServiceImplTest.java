package com.example.block7crudvalidation.applicationTest;

import com.example.block7crudvalidation.application.ProfesorServiceImpl;
import com.example.block7crudvalidation.application.StudentService;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.controller.dto.ProfesorInputDto;
import com.example.block7crudvalidation.controller.dto.ProfesorOutputDto;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.repository.PersonRepository;
import com.example.block7crudvalidation.repository.ProfesorRepository;
import com.example.block7crudvalidation.repository.StudentRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class ProfesorServiceImplTest {
    @InjectMocks
    ProfesorServiceImpl profesorService;

    @Mock
    PersonRepository personRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    ProfesorRepository profesorRepository;

    @Test
    public void comprobacionesProfesorTest(){
        Person person1 = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor1 = new Profesor(1,person1,"","",new ArrayList<>());
        ProfesorInputDto profesorInputDto = new ProfesorInputDto(1,"","");

        try{
            profesorService.comprobacionesProfesor(profesorInputDto);
        }catch (EntityNotFoundException e){
            Assertions.assertEquals(e.getMessage(),"No se encontro la persona con Id 1");
        }

        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person1));
        Mockito.when(profesorRepository.findByPersona(person1)).thenReturn(Optional.of(profesor1));
        try{
            profesorService.comprobacionesProfesor(profesorInputDto);
        }catch (EntityNotFoundException e){
            Assertions.assertEquals(e.getMessage(),"Esa persona ya esta asociada con un profesor");
        }

        Mockito.when(profesorRepository.findByPersona(person1)).thenReturn(Optional.empty());
        Mockito.when(studentRepository.findByPersona(person1)).thenReturn(Optional.of(new Student()));
        try{
            profesorService.comprobacionesProfesor(profesorInputDto);
        }catch (EntityNotFoundException e){
            Assertions.assertEquals(e.getMessage(),"Esa persona ya esta asociada con un alumno");
        }
        Mockito.when(studentRepository.findByPersona(person1)).thenReturn(Optional.empty());
        profesorService.comprobacionesProfesor(profesorInputDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void comprobacionesProfesorException1Test(){
        Person person1 = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor1 = new Profesor(1,person1,"","",new ArrayList<>());
        ProfesorInputDto profesorInputDto = new ProfesorInputDto(1,"","");
        profesorService.comprobacionesProfesor(profesorInputDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void comprobacionesProfesorException2Test() {
        Person person1 = new Person(1, "anatooa", "12345678", "andres", "anton", "andres.anton@bosonit.com",
                "ndresanton9@gmail.com", "Logroño", true, new Date(2023 - 07 - 18), "https//:8080/url.com", new Date(2023 - 07 - 18));
        Profesor profesor1 = new Profesor(1, person1, "", "", new ArrayList<>());
        ProfesorInputDto profesorInputDto = new ProfesorInputDto(1, "", "");

        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person1));
        Mockito.when(profesorRepository.findByPersona(person1)).thenReturn(Optional.of(profesor1));
        profesorService.comprobacionesProfesor(profesorInputDto);

    }
    @Test
    public void addProfesorTest(){
        Person person1 = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor1 = new Profesor(1,person1,"","",new ArrayList<>());
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person1));
        Mockito.when(profesorRepository.save(Mockito.any(Profesor.class))).thenReturn(profesor1);
        ProfesorInputDto profesorInputDto = new ProfesorInputDto(1,"","");
        ProfesorOutputDto profesorOutputDto = profesorService.addProfesor(profesorInputDto);
        Assertions.assertEquals(profesorOutputDto,profesor1.profesorToProfesorOutputDto());
    }
    @Test
    public void deleteProfesorIdTest(){
        Person person1 = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor1 = new Profesor(1,person1,"","",new ArrayList<>());
        Mockito.when(profesorRepository.findById(1)).thenReturn(Optional.of(profesor1));
        doNothing().when(profesorRepository).deleteById(1);
        profesorService.deleteProfesorId(1);

        try{
            profesorService.deleteProfesorId(2);
        }catch (EntityNotFoundException e){
            Assertions.assertEquals(e.getMessage(),"No se encontró el profesor con ID: 2");
        }
    }
    @Test
    public void updateProfesorTest(){
        Person person1 = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor1 = new Profesor(1,person1,"","",new ArrayList<>());
        ProfesorInputDto profesorInputDto = new ProfesorInputDto(null,"","");
        Mockito.when(profesorRepository.findById(1)).thenReturn(Optional.of(profesor1));
        Mockito.when(profesorRepository.save(Mockito.any(Profesor.class))).thenReturn(profesor1);
        profesorService.updateProfesor(1,profesorInputDto);
    }
    @Test
    public void getAllProfesoresTest(){
        Person person1 = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person2 = new Person(2,"anatooa","12345678","felix","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor1 = new Profesor(1,person1,"","",new ArrayList<>());
        Profesor profesor2 = new Profesor(1,person2,"","",new ArrayList<>());
        List<Profesor> listEntrada = new ArrayList<>();
        listEntrada.add(profesor1);
        listEntrada.add(profesor2);
        Page<Profesor> personPage = new PageImpl<>(listEntrada);
        PageRequest pageRequest = PageRequest.of(1, 2);
        Mockito.when(profesorRepository.findAll(pageRequest)).thenReturn(personPage);
        List<ProfesorOutputDto> listObtenida = profesorService.getAllProfesores(1,2);
        List<ProfesorOutputDto> listEsperada= listEntrada.stream().map(profesor -> profesor.profesorToProfesorOutputDto()).collect(Collectors.toList());
        Assertions.assertEquals(listEsperada,listObtenida);

        listEntrada.remove(profesor1);
        personPage = new PageImpl<>(listEntrada);
        pageRequest = PageRequest.of(2,1);
        Mockito.when(profesorRepository.findAll(pageRequest)).thenReturn(personPage);
        listObtenida = profesorService.getAllProfesores(2,1);
        Assertions.assertEquals(listObtenida.size(),1);
        Assertions.assertEquals(listObtenida.get(0),profesor2.profesorToProfesorOutputDto());
    }
    @Test
    public void getProfesorTest(){
        // Obtener profesor existente
        Person person1 = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor = new Profesor(1,person1,"","",new ArrayList<>());
        Mockito.when(profesorRepository.findById(1)).thenReturn(Optional.of(profesor));
        ProfesorOutputDto profesorOutputDto = profesorService.getProfesor(1);
        Assertions.assertEquals(profesorOutputDto,profesor.profesorToProfesorOutputDto());

        // Controlar excepción de profesor que no existe
        try{
            profesorService.getProfesor(2);
        }catch (EntityNotFoundException e){
            Assertions.assertEquals(e.getMessage(),"No se encontró el profesor con ID: 2");
        }
    }
}
