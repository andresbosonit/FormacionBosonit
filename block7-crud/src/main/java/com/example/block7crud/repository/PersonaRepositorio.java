package com.example.block7crud.repository;

import com.example.block7crud.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepositorio extends JpaRepository<Persona,Integer> {
}
