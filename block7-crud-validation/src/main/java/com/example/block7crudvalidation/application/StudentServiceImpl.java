package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.StudentInputDto;
import com.example.block7crudvalidation.controller.dto.StudentOutputDto;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.repository.PersonRepository;
import com.example.block7crudvalidation.repository.ProfesorRepository;
import com.example.block7crudvalidation.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProfesorRepository profesorRepository;
    @Override
    public StudentOutputDto addStudent(StudentInputDto studentInputDto) {
        Person person = personRepository.findById(studentInputDto.getIdPersona()).orElseThrow(() -> new EntityNotFoundException("No se encontro la persona con Id " + studentInputDto.getIdPersona()));
        Profesor profesor = profesorRepository.findById(studentInputDto.getIdProfesor()).orElseThrow(() -> new EntityNotFoundException("No se encontro el profesor con Id " + studentInputDto.getIdProfesor()));
        if(profesor.getIdPersona().getIdPersona() == studentInputDto.getIdPersona()){throw new EntityNotFoundException("Esa persona ya esta asociada con un profesor");}
        Student estudiante = new Student(studentInputDto);
        estudiante.setIdPersona(person);
        estudiante.setIdProfesor(profesor);
        return studentRepository.save(estudiante).studentToStudentOutputDto();
    }

    @Override
    public void deleteStudentId(int id) {
        Optional<Student> posibleStudent = studentRepository.findById(id);
        if(!posibleStudent.isPresent()) {throw new EntityNotFoundException("No se encontró el estudiante con ID: " + id); }
        studentRepository.deleteById(id);
    }

    @Override
    public StudentOutputDto updateStudent(StudentInputDto student) {
        Optional<Student> posibleStudent = studentRepository.findById(student.getIdStudent());
        if(!posibleStudent.isPresent()) {throw new EntityNotFoundException("No se encontró el estudiante con ID: " + student.getIdStudent()); }
        student.setIdStudent(Objects.requireNonNullElse(student.getIdStudent(), posibleStudent.get().getIdStudent()));
        //student.setIdPersona(Objects.requireNonNullElse(student.getIdPersona(), posibleStudent.get().getIdPersona()));
        student.setNumHoursWeek(Objects.requireNonNullElse(student.getNumHoursWeek(), posibleStudent.get().getNumHoursWeek()));
        //student.setIdProfesor(Objects.requireNonNullElse(student.getIdProfesor(), posibleStudent.get().getIdProfesor()));
        student.setBranch(Objects.requireNonNullElse(student.getBranch(), posibleStudent.get().getBranch()));
        return studentRepository.save(new Student(student)).studentToStudentOutputDto();
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
        Optional<Student> posibleStudent = studentRepository.findById(id);
        if(!posibleStudent.isPresent()) {throw new EntityNotFoundException("No se encontró el estudiante con ID: " + id); }
        return studentRepository.getReferenceById(id).studentToStudentOutputDto();
    }
}
