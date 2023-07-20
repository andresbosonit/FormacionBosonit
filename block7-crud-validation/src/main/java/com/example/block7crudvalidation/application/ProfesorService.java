package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.controller.dto.ProfesorInputDto;
import com.example.block7crudvalidation.controller.dto.ProfesorOutputDto;

import java.util.List;

public interface ProfesorService {
    ProfesorOutputDto addProfesor(ProfesorInputDto profesor);
    void deleteProfesorId(int id);
    ProfesorOutputDto updateProfesor(ProfesorInputDto profesor);
    List<ProfesorOutputDto> getAllProfesores(int pageNumber, int pageSize);
    ProfesorOutputDto getProfesor(int id);
}
