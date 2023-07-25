package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.ProfesorInputDto;
import com.example.block7crudvalidation.controller.dto.ProfesorOutputDto;
import com.example.block7crudvalidation.controller.dto.StudentInputDto;
import com.example.block7crudvalidation.controller.dto.StudentOutputDto;

import java.util.List;

public interface StudentService {
    StudentOutputDto addStudent(StudentInputDto student);
    void deleteStudentId(int id);
    StudentOutputDto updateStudent(Integer id, StudentInputDto student);
    List<StudentOutputDto> getAllStudents(int pageNumber, int pageSize);
    StudentOutputDto getStudent(int id);
}
