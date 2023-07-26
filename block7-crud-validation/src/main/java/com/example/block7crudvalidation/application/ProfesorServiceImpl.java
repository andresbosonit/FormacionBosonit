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

    @Autowired
    StudentService studentService;

    private Person comprobacionesProfesor(ProfesorInputDto profesorInputDto){
        Person person = personRepository.findById(profesorInputDto.getIdPersona()).orElseThrow(() -> new EntityNotFoundException("No se encontro la persona con Id " + profesorInputDto.getIdPersona()));
        Optional<Profesor> profesor = profesorRepository.findByPersona(person);
        if(profesor.isPresent()){throw new EntityNotFoundException("Esa persona ya esta asociada con un profesor");}
        Optional<Student> student = studentRepository.findByPersona(person);
        if(student.isPresent()){throw new EntityNotFoundException("Esa persona ya esta asociada con un alumno");}
        return person;
    }
    @Override
    public ProfesorOutputDto addProfesor(ProfesorInputDto profesorInputDto) {
        Person person = comprobacionesProfesor(profesorInputDto);
        Profesor profesor = new Profesor(profesorInputDto);
        profesor.setPersona(person);
        return profesorRepository.save(profesor).profesorToProfesorOutputDto();
    }

    @Override
    public void deleteProfesorId(int id) {
        profesorRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró el profesor con ID: " + id); });
        profesorRepository.deleteById(id);
    }

    @Override
    public ProfesorOutputDto updateProfesor(Integer id,ProfesorInputDto profesorInputDto) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el profesor con ID: " + id));
        if(profesorInputDto.getIdPersona() != null){
            Person persona = comprobacionesProfesor(profesorInputDto);
            profesor.setPersona(persona);
        }
        if(profesorInputDto.getComments() != null){
            profesor.setComments(profesorInputDto.getComments());
        }
        if(profesorInputDto.getBranch() != null){
            profesor.setBranch(profesorInputDto.getBranch());
        }
        return profesorRepository.save(profesor).profesorToProfesorOutputDto();
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
