package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;

import java.util.List;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto person);
    void deletePersonId(int id);
    PersonOutputDto updatePerson(PersonInputDto person);
    List<PersonOutputDto> getAllPersons(int pageNumber, int pageSize);
    PersonOutputDto getPerson(int id);
    List<PersonOutputDto> getPersonsName(String name);
}
