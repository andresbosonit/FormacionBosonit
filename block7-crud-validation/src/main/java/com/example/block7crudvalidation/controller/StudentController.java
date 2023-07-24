package com.example.block7crudvalidation.controller;

import com.example.block7crudvalidation.application.PersonService;
import com.example.block7crudvalidation.application.PersonServiceImpl;
import com.example.block7crudvalidation.application.StudentService;
import com.example.block7crudvalidation.application.StudentServiceImpl;
import com.example.block7crudvalidation.controller.dto.*;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/estudiante")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    PersonService personService;

    @PostMapping
    public ResponseEntity<StudentOutputDto> añadirEstudiante(@RequestBody StudentInputDto student){
        URI location = URI.create("/estudiante");
        StudentOutputDto studentOutputDto = studentService.addStudent(student);
        return ResponseEntity.created(location).body(studentOutputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentOutputDto> obtenerEstudianteId(@PathVariable int id,@RequestParam(defaultValue = "simple") String output) {
        StudentOutputDto studentOutputDto = studentService.getStudent(id);
        if(output.equals("simple")){
            return ResponseEntity.ok().body(studentOutputDto);
        }
        if(output.equals("full")){
            PersonOutputDto personOutputDto = personService.getPerson(studentOutputDto.getIdStudent());
            StudentOutputFullDto studentOutputFullDto = new StudentOutputFullDto(studentOutputDto,personOutputDto);
            return ResponseEntity.ok().body(studentOutputFullDto);
        }else{
            throw new EntityNotFoundException("No existe ese outputType");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> borrarEstudianteId(@RequestParam int id){
        studentService.deleteStudentId(id);
        return ResponseEntity.ok().body("El estudiante con id: "+id+" ha sido eliminada");
    }
    @GetMapping
    public Iterable<StudentOutputDto> obtenerTodosLosEstudiantes(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return studentService.getAllStudents(pageNumber, pageSize);
    }

    @PutMapping
    public ResponseEntity<StudentOutputDto> actualizarEstudiante(@RequestBody StudentInputDto student){
        return  ResponseEntity.ok().body(studentService.updateStudent(student));
    }
}
