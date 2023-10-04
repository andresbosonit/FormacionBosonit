package com.example.block7crud.application;

import com.example.block7crud.controller.dto.PersonInputDto;
import com.example.block7crud.controller.dto.PersonOutputDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ServicioPersona {
    PersonOutputDto añadirPersona(PersonInputDto person);
    PersonOutputDto modificarPersona(PersonInputDto person);

    void borrarPesona(int id);

    PersonOutputDto consultarPersonaId(int id);
    List<PersonOutputDto> consultarPersonaNombre(String nombre, int pageNumber, int pageSize);

    List<PersonOutputDto> consultarTodosLosRegistros(int pageNumber, int pageSize);
}
