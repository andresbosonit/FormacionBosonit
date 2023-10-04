package com.example.block7crud.application;

import com.example.block7crud.controller.dto.PersonInputDto;
import com.example.block7crud.controller.dto.PersonOutputDto;
import com.example.block7crud.domain.Persona;
import com.example.block7crud.repository.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioPersonaImpl implements ServicioPersona{

    @Autowired
    PersonaRepositorio personaRepositorio;
    @Override
    public PersonOutputDto añadirPersona(PersonInputDto person) {
        return personaRepositorio.save(new Persona(person)).personaToPersonaOutputDto();
    }
    @Override
    public PersonOutputDto modificarPersona(PersonInputDto person) {
        personaRepositorio.findById(person.getId()).orElseThrow();
        return personaRepositorio.save(new Persona(person)).personaToPersonaOutputDto();
    }

    @Override
    public void borrarPesona(int id) {
        personaRepositorio.findById(id).orElseThrow();
        personaRepositorio.deleteById(id);
    }

    @Override
    public PersonOutputDto consultarPersonaId(int id) {
        personaRepositorio.findById(id).orElseThrow();
        return personaRepositorio.getReferenceById(id).personaToPersonaOutputDto();
    }

    @Override
    public List<PersonOutputDto> consultarPersonaNombre(String nombre) {
        List<Persona> personas = personaRepositorio.findAll();
        List<PersonOutputDto> personasOutput = new ArrayList<>();
        for(Persona p : personas){
            if(p.getNombre().equals(nombre)){
                personasOutput.add(p.personaToPersonaOutputDto());
            }
        }
        return personasOutput;
    }

    @Override
    @Cacheable("consultarTodosLosRegistros")
    public List<PersonOutputDto> consultarTodosLosRegistros(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personaRepositorio.findAll(pageRequest).getContent()
                .stream()
                .map(Persona::personaToPersonaOutputDto).toList();
    }
}
