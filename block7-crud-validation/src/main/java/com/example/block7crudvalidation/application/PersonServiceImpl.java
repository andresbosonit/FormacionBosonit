package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.*;
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
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    PersonRepository personRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfesorRepository profesorRepository;
    @Override
    public PersonOutputDto addPerson(PersonInputDto person) throws UnprocessableEntityException {
        List<String> mensajes = new ArrayList<>();
        if (person.getUsuario()==null) {mensajes.add("Usuario no puede ser nulo");}
        else if (person.getUsuario().length()>10) {mensajes.add("Longitud de usuario no puede ser superior a 10 caracteres");}
        else if (person.getUsuario().length()<6) {mensajes.add("Longitud de usuario no puede ser inferior a 6 caracteres");}
        if (person.getPassword() == null) {mensajes.add("Contraseña no puede ser nulo");}
        if (person.getName() == null) {mensajes.add("Nombre no puede ser nulo");}
        if (person.getCompanyEmail() == null) {mensajes.add("Email de la compañia no puede ser nulo");}
        if (person.getPersonalEmail() == null) {mensajes.add("Email personal no puede ser nulo");}
        if (person.getCity() == null) {mensajes.add("Ciudad no puede ser nulo");}
        if (person.getActive() == null) {mensajes.add("Activo no puede ser nulo");}
        if (person.getCreatedDate() == null) {mensajes.add("Fecha de creacion no puede ser nulo");}
        if(mensajes.isEmpty()){
            return personRepository.save(new Person(person)).personToPersonOutputDto();
        }else{
            throw new UnprocessableEntityException(mensajes.toString());
        }
    }

    @Override
    public void deletePersonId(int id) throws EntityNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID: " + id));
        studentRepository.findByidPersona(person).ifPresent(student -> studentRepository.deleteById(student.getIdStudent()));
        profesorRepository.findByidPersona(person).ifPresent(profesor -> profesorRepository.deleteById(profesor.getIdProfesor()));
        personRepository.deleteById(id);
    }

    public Optional<Student> getStudent(int id){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID: " + id));
        return studentRepository.findByidPersona(person);
    }

    public Optional<Profesor> getProfesor(int id){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID: " + id));
        return profesorRepository.findByidPersona(person);
    }

    @Override
    public PersonOutputDto updatePerson(PersonInputDto person) {
        Optional<Person> posiblePesona = personRepository.findById(person.getIdPersona());
        if(!posiblePesona.isPresent()) {throw new EntityNotFoundException("No se encontró la persona con ID: " + person.getIdPersona()); }
        person.setUsuario(Objects.requireNonNullElse(person.getUsuario(), posiblePesona.get().getUsuario()));
        person.setPassword(Objects.requireNonNullElse(person.getPassword(), posiblePesona.get().getPassword()));
        person.setName(Objects.requireNonNullElse(person.getName(), posiblePesona.get().getName()));
        person.setSurname(Objects.requireNonNullElse(person.getSurname(), posiblePesona.get().getSurname()));
        person.setCompanyEmail(Objects.requireNonNullElse(person.getCompanyEmail(), posiblePesona.get().getCompanyEmail()));
        person.setPersonalEmail(Objects.requireNonNullElse(person.getPersonalEmail(), posiblePesona.get().getPersonalEmail()));
        person.setCity(Objects.requireNonNullElse(person.getCity(), posiblePesona.get().getCity()));
        person.setActive(Objects.requireNonNullElse(person.getActive(), posiblePesona.get().getActive()));
        person.setCreatedDate(Objects.requireNonNullElse(person.getCreatedDate(), posiblePesona.get().getCreatedDate()));
        person.setImageUrl(Objects.requireNonNullElse(person.getImageUrl(), posiblePesona.get().getImageUrl()));
        person.setTerminationDate(Objects.requireNonNullElse(person.getTerminationDate(), posiblePesona.get().getTerminationDate()));
        List<String> mensajes = new ArrayList<>();
        if (person.getUsuario().length()>10) {mensajes.add("Longitud de usuario no puede ser superior a 10 caracteres");}
        else if (person.getUsuario().length()<6) {mensajes.add("Longitud de usuario no puede ser inferior a 6 caracteres");}
        if(mensajes.isEmpty()){
            return personRepository.save(new Person(person)).personToPersonOutputDto();
        }else{
            throw new UnprocessableEntityException(mensajes.toString());
        }
    }

    @Override
    public List<PersonOutputDto> getAllPersons(int pageNumber, int pageSize, String output) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personRepository.findAll(pageRequest).getContent()
                .stream()
                .map(person -> getPersonOutputDto(person.getIdPersona(), output)).toList();
    }

    @Override
    public PersonOutputDto getPersonOutputDto(int id, String output) {
        PersonOutputDto personOutputDto = getPerson(id);
        if (output.equals("full")) {
            StudentOutputDto studentOutputDto = getStudent(id).map(Student::studentToStudentOutputDto).orElse(null);
            if (studentOutputDto != null) {
                return new PersonStudentOutputFullDto(personOutputDto, studentOutputDto);
            }
            ProfesorOutputDto profesorOutputDto = getProfesor(id).map(Profesor::profesorToProfesorOutputDto).orElse(null);
            if (profesorOutputDto != null) {
                return new PersonProfesorOutputFullDto(personOutputDto, profesorOutputDto);
            }
        }
        return personOutputDto;
    }

    @Override
    public PersonOutputDto getPerson(int id){
        Optional<Person> posiblePesona = personRepository.findById(id);
        if(!posiblePesona.isPresent()) {throw new EntityNotFoundException("No se encontró la persona con ID: " + id); }
        return personRepository.getReferenceById(id).personToPersonOutputDto();
    }

    @Override
    public List<PersonOutputDto> getPersonsName(int pageNumber, int pageSize, String name, String output) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Person> personas = personRepository.findByName(name, pageRequest);
        return personas.stream()
                .map(person -> getPersonOutputDto(person.getIdPersona(), output))
                .collect(Collectors.toList());
    }
}
