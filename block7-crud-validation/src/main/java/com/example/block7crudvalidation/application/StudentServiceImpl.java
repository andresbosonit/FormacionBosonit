package com.example.block7crudvalidation.application;

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
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProfesorRepository profesorRepository;

    @Autowired
    AsignaturaRepository asignaturaRepository;

    @Override
    public StudentOutputDto addStudent(StudentInputDto studentInputDto) {
        Person person = personRepository.findById(studentInputDto.getIdPersona()).orElseThrow(() -> new EntityNotFoundException("No se encontro la persona con Id " + studentInputDto.getIdPersona()));
        Profesor profesor = profesorRepository.findById(studentInputDto.getIdProfesor()).orElseThrow(() -> new EntityNotFoundException("No se encontro el profesor con Id " + studentInputDto.getIdProfesor()));
        Optional<Profesor> profesorOptional = profesorRepository.findByPersona(person);
        if(profesorOptional.isPresent()){throw new EntityNotFoundException("Esa persona ya esta asociada con un profesor");}
        Optional<Student> studentOptional = studentRepository.findByPersona(person);
        if(studentOptional.isPresent()){throw new EntityNotFoundException("Esa persona ya esta asociada con un estudiante");}
        Student estudiante = new Student(studentInputDto);
        estudiante.setPersona(person);
        estudiante.setProfesor(profesor);
        profesor.getStudents().add(estudiante);
        profesorRepository.save(profesor);
        return studentRepository.save(estudiante).studentToStudentOutputDto();
    }

    @Override
    public void deleteStudentId(int id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró el estudiante con ID: " + id); });
        Profesor profesor = profesorRepository.findById(student.getProfesor().getIdProfesor()).orElseThrow(() -> new EntityNotFoundException("No se encontro el profesor con Id " + student.getProfesor().getIdProfesor()));
        List<Asignatura> asignaturaList = student.getAsignaturas();
        for(Asignatura asignatura: asignaturaList){
            asignatura.getStudents().remove(student);
            asignaturaRepository.save(asignatura);
        }
        studentRepository.deleteById(id);
    }

    @Override
    public StudentOutputDto updateStudent(Integer id,StudentInputDto studentInputDto) {
        Student student = studentRepository.findById(id).
                orElseThrow(() -> {throw new EntityNotFoundException("No se encontró el estudiante con ID: " + id); });
        if(studentInputDto.getIdPersona() != null){
            Person person = personRepository.findById(studentInputDto.getIdPersona()).
                    orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la persona con ID: " + studentInputDto.getIdPersona()); });
            Optional<Profesor> profesorOptional = profesorRepository.findByPersona(person);
            if(profesorOptional.isPresent()){throw new EntityNotFoundException("Esa persona ya esta asociada con un profesor");}
            Optional<Student> studentOptional = studentRepository.findByPersona(person);
            if(studentOptional.isPresent()){throw new EntityNotFoundException("Esa persona ya esta asociada con un estudiante");}
            student.setPersona(person);
        }
        if(studentInputDto.getNumHoursWeek() != null){
            student.setNumHoursWeek(studentInputDto.getNumHoursWeek());
        }
        if(studentInputDto.getComents() != null){
            student.setComents(studentInputDto.getComents());
        }
        if(studentInputDto.getIdProfesor() != null){
            Profesor profesor = profesorRepository.findById(studentInputDto.getIdProfesor()).
                    orElseThrow(() -> {throw new EntityNotFoundException("No se encontró el profesor con ID: " + studentInputDto.getIdProfesor()); });
            student.setProfesor(profesor);
        }
        if(studentInputDto.getBranch() != null){
            student.setBranch(studentInputDto.getBranch());
        }
        return studentRepository.save(student).studentToStudentOutputDto();
    }

    @Override
    public List<StudentOutputDto> getAllStudents(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return studentRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Student::studentToStudentOutputDto).toList();
    }

    @Override
    public StudentOutputDto getStudent(int id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró el estudiante con ID: " + id); });
        return student.studentToStudentOutputDto();
    }
}
