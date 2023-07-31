package com.example.block10dockerizeapp.repository;

import com.example.block10dockerizeapp.controller.dto.PersonOutputDto;
import com.example.block10dockerizeapp.domain.Person;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByName(String name, PageRequest pageRequest);
}
