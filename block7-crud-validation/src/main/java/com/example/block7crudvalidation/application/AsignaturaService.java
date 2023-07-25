package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.AsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.AsignaturaOutputDto;
import com.example.block7crudvalidation.domain.Asignatura;

import java.util.List;

public interface AsignaturaService {
    AsignaturaOutputDto addAsignatura(AsignaturaInputDto asignaturaInputDto);
    void deleteAsignaturaId(int id);
    AsignaturaOutputDto updateAsignatura(Integer id, AsignaturaInputDto asignaturaInputDto);
    List<AsignaturaOutputDto> getAllAsignatura(int pageNumber, int pageSize);
    AsignaturaOutputDto getAsignatura(int id);

    List<AsignaturaOutputDto> getAsignaturaByIdStudent(int id);
}
