package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.exceptions.UnprocessableEntityException;
import com.example.block7crudvalidation.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Person> posiblePesona = personRepository.findById(id);
        if(!posiblePesona.isPresent()) {throw new EntityNotFoundException("No se encontró la persona con ID: " + id); }
        personRepository.deleteById(id);
    }

    @Override
    public PersonOutputDto updatePerson(PersonInputDto person) {
        Optional<Person> posiblePesona = personRepository.findById(person.getIdPersona());
        if(!posiblePesona.isPresent()) {throw new EntityNotFoundException("No se encontró la persona con ID: " + person.getIdPersona()); }
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
    public List<PersonOutputDto> getAllPersons(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Person::personToPersonOutputDto).toList();
    }

    // Porque no me obliga a propagar la excepcion EntityNotFoundException como la clase Exception del primer metodo
    // Y porque no propago la excepcion en la cabecera pero aun asi la estaba cogiendo en el try catch del controlador.
    @Override
    public PersonOutputDto getPerson(int id){
        Optional<Person> posiblePesona = personRepository.findById(id);
        if(!posiblePesona.isPresent()) {throw new EntityNotFoundException("No se encontró la persona con ID: " + id); }
        return personRepository.getReferenceById(id).personToPersonOutputDto();
    }

    @Override
    public List<PersonOutputDto> getPersonsName(String name) {
        List<Person> personas = personRepository.findAll();
        List<PersonOutputDto> personasOutput = new ArrayList<>();
        for(Person p : personas){
            if(p.getName().equals(name)){
                personasOutput.add(p.personToPersonOutputDto());
            }
        }
        return personasOutput;
    }
}
