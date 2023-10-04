package com.example.block7crud.application;

import com.example.block7crud.controller.dto.PersonInputDto;
import com.example.block7crud.controller.dto.PersonOutputDto;
import com.example.block7crud.domain.Persona;
import com.example.block7crud.repository.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioPersonaImpl implements ServicioPersona{

    @Autowired
    PersonaRepositorio personaRepositorio;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private int cont = 0;
    @Override
    public PersonOutputDto añadirPersona(PersonInputDto person) {
        String sql = "INSERT INTO persona (nombre, edad, poblacion) VALUES (?,?,?)";
        jdbcTemplate.update(sql, person.getNombre(), person.getEdad(), person.getPoblacion());

        cont++;
        String selectSql = "SELECT * FROM persona WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, new Object[]{cont}, new BeanPropertyRowMapper<>(PersonOutputDto.class));
    }
    @Override
    public PersonOutputDto modificarPersona(PersonInputDto person) {
        String sql = "UPDATE persona SET nombre = ?, edad = ?, poblacion = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, person.getNombre(), person.getEdad(), person.getPoblacion(), person.getId());
        if (rowsUpdated > 0) {
            String selectSql = "SELECT * FROM persona WHERE id = ?";
            try {
                return jdbcTemplate.queryForObject(selectSql, new Object[]{person.getId()},
                        new BeanPropertyRowMapper<>(PersonOutputDto.class));
            } catch (EmptyResultDataAccessException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void borrarPesona(int id) {
        String sql = "DELETE FROM persona WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, id);
        if (rowsUpdated == 0) {
            throw new RuntimeException();
        }
    }

    @Override
    public PersonOutputDto consultarPersonaId(int id) {
        String sql = "SELECT * FROM persona WHERE id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(PersonOutputDto.class));
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<PersonOutputDto> consultarPersonaNombre(String nombre, int pageNumber, int pageSize) {
        String sql = "SELECT * FROM persona WHERE nombre = ? LIMIT ?, ?";
        int offset = pageNumber * pageSize;
        Object[] args = {nombre, offset, pageSize};
        List<PersonOutputDto> personasOutput = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<>(PersonOutputDto.class));
        return personasOutput;
    }

    @Override
    public List<PersonOutputDto> consultarTodosLosRegistros(int pageNumber, int pageSize) {
        String sql = "SELECT * FROM persona LIMIT ?, ?";
        int offset = pageNumber * pageSize;
        Object[] args = {offset, pageSize};
        List<PersonOutputDto> personas = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<>(PersonOutputDto.class));
        return personas;
    }
}
