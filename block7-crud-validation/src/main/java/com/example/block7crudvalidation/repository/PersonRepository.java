package com.example.block7crudvalidation.repository;

import com.example.block7crudvalidation.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
