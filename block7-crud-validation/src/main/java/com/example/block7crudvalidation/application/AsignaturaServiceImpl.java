package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.AsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.AsignaturaOutputDto;
import com.example.block7crudvalidation.domain.Asignatura;
import com.example.block7crudvalidation.domain.Profesor;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.repository.AsignaturaRepository;
import com.example.block7crudvalidation.repository.ProfesorRepository;
import com.example.block7crudvalidation.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {
    @Autowired
    AsignaturaRepository asignaturaRepository;

    @Autowired
    StudentRepository studentRepository;

    public List<Student> getStudentsFromIds(List<Integer> studentIds) {
        if(studentIds != null) {
            return studentIds.stream()
                    .map(studentId -> studentRepository.findById(studentId)
                            .orElseThrow(() -> new EntityNotFoundException("No se encontró el estudiante con Id " + studentId)))
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }
    @Override
    public AsignaturaOutputDto addAsignatura(AsignaturaInputDto asignaturaInputDto) {
        List<Student> studentList = getStudentsFromIds(asignaturaInputDto.getStudents());
        Asignatura asignatura = new Asignatura(asignaturaInputDto);
        asignatura.setStudents(studentList);
        AsignaturaOutputDto asignaturaOutputDto = asignaturaRepository.save(asignatura).AsignaturaTOAsignaturaOutputDto();
        studentList.forEach(student -> {
            student.getAsignaturas().add(asignatura);
            studentRepository.save(student);
        });
        return asignaturaOutputDto;
    }

    @Override
    public void deleteAsignaturaId(int id) {
        Asignatura asignatura = asignaturaRepository.findById(id).orElseThrow(() ->{throw new EntityNotFoundException("No se encontró el Asignatura con ID: " + id);});
        List<Student> studentList = asignatura.getStudents();
        studentList.forEach(student -> {
            student.getAsignaturas().remove(asignatura);
            studentRepository.save(student);
        });
        asignaturaRepository.deleteById(id);
    }

    @Override
    public AsignaturaOutputDto updateAsignatura(Integer id, AsignaturaInputDto asignaturaInputDto) {
        Asignatura asignatura = asignaturaRepository.findById(id).
            orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la asignatura con ID: " + id); });
        if(asignaturaInputDto.getStudents() != null){
            List<Student> studentList = asignatura.getStudents();
            studentList.forEach(student -> {
                student.getAsignaturas().remove(asignatura);
                studentRepository.save(student);
            });
            studentList = getStudentsFromIds(asignaturaInputDto.getStudents());
            studentList.forEach(student -> {
                student.getAsignaturas().add(asignatura);
                studentRepository.save(student);
            });
            asignatura.setStudents(getStudentsFromIds(asignaturaInputDto.getStudents()));
        }
        if(asignaturaInputDto.getAsignatura() != null){
            asignatura.setAsignatura(asignaturaInputDto.getAsignatura());
        }
        if(asignaturaInputDto.getComments() != null){
            asignatura.setComments(asignaturaInputDto.getComments());
        }
        if(asignaturaInputDto.getInitialDate() != null){
            asignatura.setInitialDate(asignaturaInputDto.getInitialDate());
        }
        if(asignaturaInputDto.getFinishDate() != null){
            asignatura.setFinishDate(asignaturaInputDto.getFinishDate());
        }
        return asignaturaRepository.save(asignatura).AsignaturaTOAsignaturaOutputDto();
    }

    @Override
    public List<AsignaturaOutputDto> getAllAsignatura(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return asignaturaRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Asignatura::AsignaturaTOAsignaturaOutputDto).toList();
    }

    @Override
    public AsignaturaOutputDto getAsignatura(int id) {
        Asignatura asignatura = asignaturaRepository.findById(id).orElseThrow(() ->{throw new EntityNotFoundException("No se encontró el Asignatura con ID: " + id); });
        return asignatura.AsignaturaTOAsignaturaOutputDto();
    }

    @Override
    public List<AsignaturaOutputDto> getAsignaturaByIdStudent(int id) {
        Student student = studentRepository.findById(id).orElseThrow(() ->{throw new EntityNotFoundException("No se encontró el estudiante con ID: " + id); });
        return student.getAsignaturas().stream().map(asignatura -> asignatura.AsignaturaTOAsignaturaOutputDto()).toList();
    }
}
