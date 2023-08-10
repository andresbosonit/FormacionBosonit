package com.example.block7crudvalidation.services;

import com.example.block7crudvalidation.application.ProfesorServiceImpl;
import com.example.block7crudvalidation.application.StudentServiceImpl;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.controller.dto.StudentInputDto;
import com.example.block7crudvalidation.controller.dto.StudentOutputDto;
import com.example.block7crudvalidation.domain.Asignatura;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.repository.AsignaturaRepository;
import com.example.block7crudvalidation.repository.PersonRepository;
import com.example.block7crudvalidation.repository.ProfesorRepository;
import com.example.block7crudvalidation.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {
    @InjectMocks
    StudentServiceImpl studentService;

    @Mock
    PersonRepository personRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    ProfesorRepository profesorRepository;

    @Mock
    AsignaturaRepository asignaturaRepository;

    @Test
    public void getAsignaturasFromIdsTest(){
        Asignatura asignatura1 = new Asignatura(1,new ArrayList<>(),"","",new Date(),new Date());
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        Mockito.when(asignaturaRepository.findById(1)).thenReturn(Optional.of(asignatura1));
        studentService.getAsignaturasFromIds(integerList);
    }
    @Test
    public void addStudentTest(){
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person1 = new Person(2,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor = new Profesor(1,person,"","",new ArrayList<>());
        Student student = new Student(1,person1,12,"",profesor,"",new ArrayList<>());
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person));
        Mockito.when(profesorRepository.findById(1)).thenReturn(Optional.of(profesor));
        Mockito.when(profesorRepository.findByPersona(Mockito.any(Person.class))).thenReturn(Optional.empty());
        Mockito.when(studentRepository.findByPersona(Mockito.any(Person.class))).thenReturn(Optional.empty());
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        Mockito.when(profesorRepository.save(Mockito.any(Profesor.class))).thenReturn(profesor);
        StudentInputDto studentInputDto = new StudentInputDto(1,12,"",1,"");
        StudentOutputDto studentOutputDto = studentService.addStudent(studentInputDto);
        Assertions.assertEquals(studentOutputDto,student.studentToStudentOutputDto());
    }

    @Test
    public void addStudentTestException1(){
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person1 = new Person(2,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor = new Profesor(1,person,"","",new ArrayList<>());
        StudentInputDto studentInputDto = new StudentInputDto(1,12,"",1,"");
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person));
        Mockito.when(profesorRepository.findById(1)).thenReturn(Optional.of(profesor));
        Mockito.when(profesorRepository.findByPersona(Mockito.any(Person.class))).thenReturn(Optional.of(profesor));
        Assertions.assertThrows(EntityNotFoundException.class, () -> studentService.addStudent(studentInputDto));
    }

    @Test
    public void addStudentTestException2(){
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person1 = new Person(2,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor = new Profesor(1,person,"","",new ArrayList<>());
        Student student = new Student(1,person1,12,"",profesor,"",new ArrayList<>());
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person));
        Mockito.when(profesorRepository.findById(1)).thenReturn(Optional.of(profesor));
        Mockito.when(profesorRepository.findByPersona(Mockito.any(Person.class))).thenReturn(Optional.empty());
        Mockito.when(studentRepository.findByPersona(Mockito.any(Person.class))).thenReturn(Optional.of(student));
        StudentInputDto studentInputDto = new StudentInputDto(1,12,"",1,"");
        Assertions.assertThrows(EntityNotFoundException.class, () -> studentService.addStudent(studentInputDto));
    }

    @Test
    public void deleteStudentIdTest(){
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person1 = new Person(2,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor = new Profesor(1,person,"","",new ArrayList<>());
        Student student = new Student(1,person1,12,"",profesor,"",new ArrayList<>());
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Mockito.when(profesorRepository.findById(1)).thenReturn(Optional.of(profesor));
        studentService.deleteStudentId(1);
    }
    @Test
    public void updateStudentTest(){
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person1 = new Person(2,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor = new Profesor(1,person,"","",new ArrayList<>());
        Student student = new Student(1,person1,12,"",profesor,"",new ArrayList<>());
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person));
        Mockito.when(profesorRepository.findByPersona(Mockito.any(Person.class))).thenReturn(Optional.empty());
        Mockito.when(studentRepository.findByPersona(Mockito.any(Person.class))).thenReturn(Optional.empty());
        Mockito.when(profesorRepository.findById(1)).thenReturn(Optional.of(profesor));
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        StudentInputDto studentInputDto = new StudentInputDto(1,12,"",1,"");
        studentService.updateStudent(1,studentInputDto);
    }
    @Test
    public void getAllStudentsTest(){
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person1 = new Person(2,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor = new Profesor(1,person,"","",new ArrayList<>());
        Student student = new Student(1,person1,12,"",profesor,"",new ArrayList<>());

        List<Student> listEntrada = new ArrayList<>();
        listEntrada.add(student);
        Page<Student> studentPage = new PageImpl<>(listEntrada);
        PageRequest pageRequest = PageRequest.of(1, 1);
        Mockito.when(studentRepository.findAll(pageRequest)).thenReturn(studentPage);
        List<StudentOutputDto> listObtenida = studentService.getAllStudents(1,1);
        List<StudentOutputDto> listEsperada= listEntrada.stream().map(student1 -> student1.studentToStudentOutputDto()).collect(Collectors.toList());
        Assertions.assertEquals(listEsperada,listObtenida);
    }
    @Test
    public void getStudentTest(){
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person1 = new Person(2,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor = new Profesor(1,person,"","",new ArrayList<>());
        Student student = new Student(1,person1,12,"",profesor,"",new ArrayList<>());
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        StudentOutputDto studentOutputDto = studentService.getStudent(1);
        Assertions.assertEquals(studentOutputDto,student.studentToStudentOutputDto());
    }
    @Test
    public void AñadirAsignaturasAEstudianteIdTest(){
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person1 = new Person(2,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor = new Profesor(1,person,"","",new ArrayList<>());
        Student student = new Student(1,person1,12,"",profesor,"",new ArrayList<>());
        Asignatura asignatura = new Asignatura(1,new ArrayList<>(),"","",new Date(),new Date());
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Mockito.when(asignaturaRepository.findById(1)).thenReturn(Optional.of(asignatura));
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        studentService.AñadirAsignaturasAEstudianteId(1,integerList);
    }
    @Test
    public void QuitarAsignaturasAEstudianteIdTest(){
        Person person = new Person(1,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Person person1 = new Person(2,"anatooa","12345678","andres","anton","andres.anton@bosonit.com",
                "ndresanton9@gmail.com","Logroño",true,new Date(2023-07-18),"https//:8080/url.com",new Date(2023-07-18));
        Profesor profesor = new Profesor(1,person,"","",new ArrayList<>());
        Asignatura asignatura = new Asignatura(1,new ArrayList<>(),"","",new Date(),new Date());
        List<Asignatura> asignaturaList = new ArrayList<>();
        asignaturaList.add(asignatura);
        Student student = new Student(1,person1,12,"",profesor,"",asignaturaList);
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Mockito.when(asignaturaRepository.findById(1)).thenReturn(Optional.of(asignatura));
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        studentService.QuitarAsignaturasAEstudianteId(1,integerList);
    }
}
