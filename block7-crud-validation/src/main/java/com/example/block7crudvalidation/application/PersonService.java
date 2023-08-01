package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.controller.dto.ProfesorOutputDto;

import java.util.HashMap;
import java.util.List;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto person);
    void deletePersonId(int id);
    PersonOutputDto updatePerson(Integer idPerson,PersonInputDto person);
    PersonOutputDto getPersonOutputDto(int id, String output);
    List<PersonOutputDto> getAllPersons(int pageNumber, int pageSize, String output) ;
    PersonOutputDto getPerson(int id);
    List<PersonOutputDto> getPersonsName(int pageNumber, int pageSize, String name, String output) ;
    List<PersonOutputDto> getCustomQuery(HashMap<String, Object> conditions);
}
