package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.EstudianteAsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.EstudianteAsignaturaOutputDto;
import com.example.block7crudvalidation.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.controller.dto.PersonOutputDto;

import java.util.List;

public interface EstudianteAsignaturaService {
    EstudianteAsignaturaOutputDto addEstudianteAsignatura(EstudianteAsignaturaInputDto estudianteAsignaturaInputDto);
    void deleteEstudianteAsignaturaId(int id);
    EstudianteAsignaturaOutputDto updateEstudianteAsignatura(EstudianteAsignaturaInputDto estudianteAsignaturaInputDto);
    List<EstudianteAsignaturaOutputDto> getAllEstudianteAsignatura(int pageNumber, int pageSize);
    EstudianteAsignaturaOutputDto getEstudianteAsignatura(int id);
}
