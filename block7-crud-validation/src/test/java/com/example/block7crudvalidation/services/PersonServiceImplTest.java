package com.example.block7crudvalidation.services;

import com.example.block7crudvalidation.application.PersonServiceImpl;
import com.example.block7crudvalidation.controller.dto.*;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.exceptions.UnprocessableEntityException;
import com.example.block7crudvalidation.repository.PersonRepository;
import com.example.block7crudvalidation.repository.ProfesorRepository;
import com.example.block7crudvalidation.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doNothing;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
    @InjectMocks
    PersonServiceImpl personService;
    @Mock
    PersonRepository personRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    ProfesorRepository profesorRepository;

    @Test
    public void addPersonTest(){
        // Person null
        PersonInputDto personNull = new PersonInputDto();
        try{
            personService.addPerson(personNull);
        }catch (UnprocessableEntityException e){
            Assertions.assertEquals(e.getMessage(),"[Usuario no puede ser nulo, Contraseña no puede ser nulo, Nombre no puede ser nulo, Email de la compañia no puede ser nulo, Email personal no puede ser nulo, Ciudad no puede ser nulo, Activo no puede ser nulo, Fecha de creacion no puede ser nulo]");
        }

        // Longitud usuario < 6
        PersonInputDto personUsuarioLenght = new PersonInputDto("jesus","12345678","jesus","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        try{
            personService.addPerson(personUsuarioLenght);
        }catch (UnprocessableEntityException e){
            Assertions.assertEquals(e.getMessage(),"[Longitud de usuario no puede ser inferior a 6 caracteres]");
        }

        // Longitud usuario > 6
        personUsuarioLenght.setUsuario("jesusjesusjesus");
        try{
            personService.addPerson(personUsuarioLenght);
        }catch (UnprocessableEntityException e){
            Assertions.assertEquals(e.getMessage(),"[Longitud de usuario no puede ser superior a 10 caracteres]");
        }

        // Inserción correcta
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));

        PersonInputDto personInputDto = new PersonInputDto("anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));

        PersonOutputDto personOutputDto = new PersonOutputDto(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));

        Mockito.when(personRepository.save(new Person(personInputDto))).thenReturn(person);

        PersonOutputDto personOutputDtoCreado = personService.addPerson(personInputDto);

        Assertions.assertEquals(personOutputDtoCreado,personOutputDto);
    }
    @Test
    public void deletePersonIdTest(){
        // Borrando persona existente
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person));
        doNothing().when(personRepository).deleteById(1);
        personService.deletePersonId(1);

        // Borrando persona pero no la encuentra
        try{
            personService.deletePersonId(2);
        }catch (EntityNotFoundException e){
            Assertions.assertEquals(e.getMessage(),"No se encontró la persona con ID: 2");
        }
    }
    @Test
    public void updatePersonTest(){
        PersonInputDto personInputDto = new PersonInputDto("anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        // Persona no encontrada
        try{
            personService.updatePerson(1, personInputDto);
        }catch (EntityNotFoundException e){
            Assertions.assertEquals(e.getMessage(),"No se encontró la persona con ID: 1");
        }


        // Intentando cambiar la longitud del usuario a menos de 6
        personInputDto.setUsuario("je");
        Person person = new Person(1, "anatooa", "12345678", "andres", "anton", "andres.anton@bosonit.com",
                "ndresanton9@gmail.com", "Logroño", true, new Date(2023-07-18), "https//:8080/url.com", new Date(2023-07-18));
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person));
        try{
            personService.updatePerson(1,personInputDto);
        }catch (UnprocessableEntityException e){
            Assertions.assertEquals(e.getMessage(),"[Longitud de usuario no puede ser inferior a 6 caracteres]");
        }

        // Intentando cambiar la longitud del usuario a mas de 10
        personInputDto.setUsuario("jesusjesusjesus");
        try{
            personService.updatePerson(1,personInputDto);
        }catch (UnprocessableEntityException e){
            Assertions.assertEquals(e.getMessage(),"[Longitud de usuario no puede ser superior a 10 caracteres]");
        }

        // Actualizando de manera correcta
        PersonOutputDto personOutputDto = new PersonOutputDto(1, "jesusito", "12345678", "andres", "anton", "andres.anton@bosonit.com",
                "ndresanton9@gmail.com", "Logroño", true, new Date(2023-07-18), "https//:8080/url.com", new Date(2023-07-18));
        personInputDto.setUsuario("jesusito");
        Mockito.when(personRepository.save(new Person(personInputDto))).thenReturn(person);
        PersonOutputDto personOutputDtoCreado = personService.updatePerson(1,personInputDto);
        Assertions.assertEquals(personOutputDtoCreado,personOutputDto);
    }
    @Test
    public void getPersonOutputDtoTest(){
        Person personStudent = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person personProfesor = new Person(2,"pepeee","12345678","pepe","perez","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person = new Person(3,"ramonnn","12345678","ramon","perez","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));

        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(personStudent));
        Mockito.when(personRepository.findById(2)).thenReturn(Optional.of(personProfesor));
        Mockito.when(personRepository.findById(3)).thenReturn(Optional.of(person));

        // Cuando output es simple
        PersonOutputDto personOutputDto = new PersonOutputDto(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        PersonOutputDto personOutputDtoCreado = personService.getPersonOutputDto(1,"simple");
        Assertions.assertEquals(personOutputDtoCreado,personOutputDto);

        // Cuando el output es full y estudiante
        Profesor profesor = new Profesor(1,personProfesor,"hola","pepe",new ArrayList<>());
        Student student = new Student(1,personStudent,22,"hola",profesor,"pepe",new ArrayList<>());
        StudentOutputDto studentOutputDto = new StudentOutputDto(1,1,22,"hola",1,"pepe");
        Mockito.when(studentRepository.findByPersona(personStudent)).thenReturn(Optional.of(student));
        personOutputDtoCreado = personService.getPersonOutputDto(1,"full");
        Assertions.assertEquals(personOutputDtoCreado,new PersonStudentOutputFullDto(personOutputDto,studentOutputDto));

        // Cuando el output es full y profesor
        ProfesorOutputDto profesorOutputDto = new ProfesorOutputDto(1,2,"hola","pepe");
        Mockito.when(profesorRepository.findByPersona(personProfesor)).thenReturn(Optional.of(profesor));
        personOutputDtoCreado = personService.getPersonOutputDto(2,"full");
        Assertions.assertEquals(personOutputDtoCreado,new PersonProfesorOutputFullDto(personProfesor.personToPersonOutputDto(),profesorOutputDto));

        // Cuando el output es full pero no es ni profesor ni estudiante
        personOutputDtoCreado = personService.getPersonOutputDto(3,"full");
        Assertions.assertEquals(personOutputDtoCreado,person.personToPersonOutputDto());

        // Cuando no existe una persona con ese ID
        try{
            personService.getPersonOutputDto(4,"full");
        }catch (EntityNotFoundException e){
            Assertions.assertEquals(e.getMessage(),"No se encontró la persona con ID: 4");
        }
    }
    @Test
    public void getAllPersonsTest(){
        // Lista con dos personas y que devuelva las dos
        Person person1 = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person2 = new Person(2,"anatooa","12345678","felix","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));

        List<Person> listEntrada = new ArrayList<>();
        listEntrada.add(person1);
        listEntrada.add(person2);
        Page<Person> personPage = new PageImpl<>(listEntrada);
        PageRequest pageRequest = PageRequest.of(1, 2);
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person1));
        Mockito.when(personRepository.findById(2)).thenReturn(Optional.of(person2));
        Mockito.when(personRepository.findAll(pageRequest)).thenReturn(personPage);
        List<PersonOutputDto> listObtenida = personService.getAllPersons(1,2,"simple");
        List<PersonOutputDto> listEsperada= listEntrada.stream().map(person -> person.personToPersonOutputDto()).collect(Collectors.toList());
        Assertions.assertEquals(listEsperada,listObtenida);

        // Lista con dos personas y que devuelva el primer resultado de la segunda pagina
        listEntrada.remove(person1);
        personPage = new PageImpl<>(listEntrada);
        pageRequest = PageRequest.of(2,1);
        Mockito.when(personRepository.findAll(pageRequest)).thenReturn(personPage);
        listObtenida = personService.getAllPersons(2,1,"simple");
        Assertions.assertEquals(listObtenida.size(),1);
        Assertions.assertEquals(listObtenida.get(0),person2.personToPersonOutputDto());
    }
    @Test
    public void getPersonTest(){
        // Obtener persona existente
        Person person1 = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person1));
        PersonOutputDto personOutputDto = personService.getPerson(1);
        Assertions.assertEquals(personOutputDto,person1.personToPersonOutputDto());

        // Controlar excepción de persona que no existe
        try{
            personService.getPerson(2);
        }catch (EntityNotFoundException e){
            Assertions.assertEquals(e.getMessage(),"No se encontró la persona con ID: 2");
        }
    }
    @Test
    public void getPersonsNameTest(){
        // Lista con dos personas que se llaman igual
        Person person1 = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person2 = new Person(2,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));

        List<Person> listEntrada = new ArrayList<>();
        listEntrada.add(person1);
        listEntrada.add(person2);
        PageRequest pageRequest = PageRequest.of(1, 2);
        Mockito.when(personRepository.findByName("andres", pageRequest)).thenReturn(listEntrada);
        Mockito.when(personRepository.findByName("felix", pageRequest)).thenReturn(new ArrayList<>());
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person1));
        Mockito.when(personRepository.findById(2)).thenReturn(Optional.of(person2));
        List<PersonOutputDto> listObtenida = personService.getPersonsName(1,2,"andres","simple");
        List<PersonOutputDto> listEsperada= listEntrada.stream().map(person -> person.personToPersonOutputDto()).collect(Collectors.toList());
        Assertions.assertEquals(listEsperada,listObtenida);

        // Lista que no encuentra ninguna persona
        listObtenida = personService.getPersonsName(1,2,"felix","simple");
        listEsperada= new ArrayList<>();
        Assertions.assertEquals(listEsperada,listObtenida);
    }

    @Test
    public void getCustomQueryTest(){
    }
}
