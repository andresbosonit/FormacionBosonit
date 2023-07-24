package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.ProfesorInputDto;
import com.example.block7crudvalidation.controller.dto.ProfesorOutputDto;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.exceptions.UnprocessableEntityException;
import com.example.block7crudvalidation.repository.PersonRepository;
import com.example.block7crudvalidation.repository.ProfesorRepository;
import com.example.block7crudvalidation.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfesorServiceImpl implements ProfesorService {
    @Autowired
    ProfesorRepository profesorRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    StudentRepository studentRepository;
    @Override
    public ProfesorOutputDto addProfesor(ProfesorInputDto profesorInputDto) {
        Person person = personRepository.findById(profesorInputDto.getIdPersona()).orElseThrow(() -> new EntityNotFoundException("No se encontro la persona con Id " + profesorInputDto.getIdPersona()));
        Optional<Student> student = studentRepository.findByidPersona(person);
        if(student.isPresent()){throw new EntityNotFoundException("Esa persona ya esta asociada con un alumno");}
        Profesor profesor = new Profesor(profesorInputDto);
        profesor.setIdPersona(person);
        return profesorRepository.save(profesor).profesorToProfesorOutputDto();
    }

    @Override
    public void deleteProfesorId(int id) {
        Optional<Profesor> posibleProfesor = profesorRepository.findById(id);
        if(!posibleProfesor.isPresent()) {throw new EntityNotFoundException("No se encontró el profesor con ID: " + id); }
        profesorRepository.deleteById(id);
    }

    @Override
    public ProfesorOutputDto updateProfesor(ProfesorInputDto profesorInputDto) {
        /*
        Optional<Profesor> posibleProfesor = profesorRepository.findById(profesorInputDto.getIdProfesor());
        if(!posibleProfesor.isPresent()) {throw new EntityNotFoundException("No se encontró el profesor con ID: " + profesorInputDto.getIdPersona());}
        Profesor profesor = new Profesor(profesorInputDto);
        profesor.setIdProfesor(Objects.requireNonNullElse(profesor.getIdProfesor(), posibleProfesor.get().getIdProfesor()));
        if ()
        var pp =personRepository.findById(profesorInputDto.getIdPersona()).orElseThrow();
        profesor.setIdPersona(Objects.requireNonNullElse(pp, posibleProfesor.get().getIdPersona()));
        profesor.setBranch(Objects.requireNonNullElse(profesor.getBranch(), posibleProfesor.get().getBranch()));
        return profesorRepository.save(profesor).profesorToProfesorOutputDto();*/
        return null;
    }

    @Override
    public List<ProfesorOutputDto> getAllProfesores(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return profesorRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Profesor::profesorToProfesorOutputDto).toList();
    }

    @Override
    public ProfesorOutputDto getProfesor(int id) {
        Optional<Profesor> posiblePesona = profesorRepository.findById(id);
        if(!posiblePesona.isPresent()) {throw new EntityNotFoundException("No se encontró el profesor con ID: " + id); }
        return profesorRepository.getReferenceById(id).profesorToProfesorOutputDto();
    }
}
