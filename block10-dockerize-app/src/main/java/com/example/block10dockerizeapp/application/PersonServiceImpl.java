package com.example.block10dockerizeapp.application;

import com.example.block10dockerizeapp.controller.dto.*;
import com.example.block10dockerizeapp.domain.Person;
import com.example.block10dockerizeapp.exceptions.EntityNotFoundException;
import com.example.block10dockerizeapp.exceptions.UnprocessableEntityException;
import com.example.block10dockerizeapp.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    PersonRepository personRepository;
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
        personRepository.deleteById(id);
    }


    @Override
    public PersonOutputDto updatePerson(Integer idPersona, PersonInputDto person) {
        Person persona = personRepository.findById(idPersona).orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID: " + idPersona));
        persona.setUsuario(Objects.requireNonNullElse(person.getUsuario(), persona.getUsuario()));
        persona.setPassword(Objects.requireNonNullElse(person.getPassword(), persona.getPassword()));
        persona.setName(Objects.requireNonNullElse(person.getName(), persona.getName()));
        if(person.getSurname() != null){
            persona.setSurname(person.getSurname());
        }
        persona.setCompanyEmail(Objects.requireNonNullElse(person.getCompanyEmail(), persona.getCompanyEmail()));
        persona.setPersonalEmail(Objects.requireNonNullElse(person.getPersonalEmail(), persona.getPersonalEmail()));
        persona.setCity(Objects.requireNonNullElse(person.getCity(), persona.getCity()));
        persona.setActive(Objects.requireNonNullElse(person.getActive(), persona.getActive()));
        persona.setCreatedDate(Objects.requireNonNullElse(person.getCreatedDate(), persona.getCreatedDate()));
        if(person.getImageUrl() != null){
            persona.setImageUrl(person.getImageUrl());
        }
        if(person.getTerminationDate() != null){
            persona.setTerminationDate(person.getTerminationDate());
        }
        List<String> mensajes = new ArrayList<>();
        if (persona.getUsuario().length()>10) {mensajes.add("Longitud de usuario no puede ser superior a 10 caracteres");}
        else if (persona.getUsuario().length()<6) {mensajes.add("Longitud de usuario no puede ser inferior a 6 caracteres");}
        if(mensajes.isEmpty()){
            return personRepository.save(persona).personToPersonOutputDto();
        }else{
            throw new UnprocessableEntityException(mensajes.toString());
        }
    }

    @Override
    public List<PersonOutputDto> getAllPersons(int pageNumber, int pageSize, String output) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personRepository.findAll(pageRequest).getContent()
                .stream()
                .map(person -> person.personToPersonOutputDto()).toList();
    }


    @Override
    public PersonOutputDto getPerson(int id){
        Person persona = personRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la persona con ID: " + id); });
        return persona.personToPersonOutputDto();
    }

    @Override
    public List<PersonOutputDto> getPersonsName(int pageNumber, int pageSize, String name, String output) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Person> personas = personRepository.findByName(name, pageRequest);
        return personas.stream()
                .map(person -> person.personToPersonOutputDto())
                .collect(Collectors.toList());
    }
}
