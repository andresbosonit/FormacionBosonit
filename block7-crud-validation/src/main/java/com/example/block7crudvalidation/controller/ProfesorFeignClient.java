package com.example.block7crudvalidation.controller;

import com.example.block7crudvalidation.controller.dto.ProfesorOutputDto;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profesorClient", url = "http://localhost:8081")
public interface ProfesorFeignClient {
    @GetMapping("/profesor/{id}")
    ProfesorOutputDto getProfesor(@PathVariable int id);
}