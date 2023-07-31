package com.example.block11uploaddownloadfiles.repository;

import com.example.block11uploaddownloadfiles.domain.Fichero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FicheroRepository extends JpaRepository<Fichero, Integer> {
    Optional<Fichero> findByNombre(String nombre);
}
