package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    PersonRepository personRepository;
    @Override
    public PersonOutputDto addPerson(PersonInputDto person) throws Exception {
        if (person.getUsuario()==null) {throw new Exception("Usuario no puede ser nulo"); }
        if (person.getUsuario().length()>10) { throw  new Exception("Longitud de usuario no puede ser superior a 10 caracteres");}
        if (person.getUsuario().length()<6) { throw  new Exception("Longitud de usuario no puede ser inferior a 6 caracteres");}
        if (person.getPassword() == null) {throw new Exception("Contraseña no puede ser nulo"); }
        if (person.getName() == null) {throw new Exception("Nombre no puede ser nulo"); }
        if (person.getCompanyEmail() == null) {throw new Exception("Email de la compañia no puede ser nulo"); }
        if (person.getPersonalEmail() == null) {throw new Exception("Email personal no puede ser nulo"); }
        if (person.getCity() == null) {throw new Exception("Ciudad no puede ser nulo"); }
        if (person.getActive() == null) {throw new Exception("Activo no puede ser nulo"); }
        if (person.getCreatedDate() == null) {throw new Exception("Fecha de creacion no puede ser nulo"); }
        return personRepository.save(new Person(person)).personToPersonOutputDto();
    }

    @Override
    public void deletePersonId(int id) {
        personRepository.findById(id).orElseThrow();
        personRepository.deleteById(id);
    }

    @Override
    public PersonOutputDto updatePerson(PersonInputDto person) {
        personRepository.findById(person.getIdPersona()).orElseThrow();
        return personRepository.save(new Person(person)).personToPersonOutputDto();
    }

    @Override
    public List<PersonOutputDto> getAllPersons(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Person::personToPersonOutputDto).toList();
    }

    @Override
    public PersonOutputDto getPerson(int id) {
        personRepository.findById(id).orElseThrow();
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
