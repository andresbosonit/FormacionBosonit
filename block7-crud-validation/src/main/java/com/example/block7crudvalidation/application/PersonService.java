package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;

import java.util.List;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto person);
    void deletePersonId(int id);
    PersonOutputDto updatePerson(PersonInputDto person);
    PersonOutputDto getPersonOutputDto(int id, String output);
    List<PersonOutputDto> getAllPersons(int pageNumber, int pageSize, String output) ;
    PersonOutputDto getPerson(int id);
    List<PersonOutputDto> getPersonsName(int pageNumber, int pageSize, String name, String output) ;
}
