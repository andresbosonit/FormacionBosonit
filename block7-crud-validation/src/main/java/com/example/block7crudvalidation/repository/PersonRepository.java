package com.example.block7crudvalidation.repository;

import com.example.block7crudvalidation.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.domain.Person;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByName(String name, PageRequest pageRequest);
}
