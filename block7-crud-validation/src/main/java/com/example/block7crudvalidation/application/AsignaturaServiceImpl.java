package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.AsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.AsignaturaOutputDto;
import com.example.block7crudvalidation.domain.Asignatura;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.repository.AsignaturaRepository;
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
    @Override
    public AsignaturaOutputDto addAsignatura(AsignaturaInputDto asignaturaInputDto) {
        List<Student> studentList = new ArrayList<>();
        for(Integer studentId: asignaturaInputDto.getIdStudent()){
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("No se encontro el  con Id " + asignaturaInputDto.getIdStudent()));
            studentList.add(student);
        }
        Asignatura asignatura = new Asignatura(asignaturaInputDto);
        asignatura.setIdStudent(studentList);
        return asignaturaRepository.save(asignatura).AsignaturaTOAsignaturaOutputDto();
    }

    @Override
    public void deleteAsignaturaId(int id) {
        Optional<Asignatura> posibleAsignatura = asignaturaRepository.findById(id);
        if(!posibleAsignatura.isPresent()) {throw new EntityNotFoundException("No se encontró el Asignatura con ID: " + id); }
        asignaturaRepository.deleteById(id);
    }

    @Override
    public AsignaturaOutputDto updateAsignatura(AsignaturaInputDto asignaturaInputDto) {
        Optional<Asignatura> posibleAsignatura = asignaturaRepository.findById(asignaturaInputDto.getIdAsignatura());
        if(!posibleAsignatura.isPresent()) {throw new EntityNotFoundException("No se encontró el Asignatura con ID: " + asignaturaInputDto.getIdAsignatura()); }
        asignaturaInputDto.setIdAsignatura(Objects.requireNonNullElse(asignaturaInputDto.getIdAsignatura(), posibleAsignatura.get().getIdAsignatura()));
        //Asignatura.setIdStudent(Objects.requireNonNullElse(Asignatura.getIdStudent(), posibleAsignatura.get().getIdStudent()));
        asignaturaInputDto.setInitialDate(Objects.requireNonNullElse(asignaturaInputDto.getInitialDate(), posibleAsignatura.get().getInitialDate()));
        return asignaturaRepository.save(new Asignatura(asignaturaInputDto)).AsignaturaTOAsignaturaOutputDto();
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
        Optional<Asignatura> posibleAsignatura = asignaturaRepository.findById(id);
        if(!posibleAsignatura.isPresent()) {throw new EntityNotFoundException("No se encontró el Asignatura con ID: " + id); }
        return asignaturaRepository.getReferenceById(id).AsignaturaTOAsignaturaOutputDto();
    }

    @Override
    public List<AsignaturaOutputDto> getAsignaturaByIdStudent(int id) {
        List<Student> studentList = new ArrayList<>();
        Student student = new Student();
        student.setIdStudent(id);
        studentList.add(student);
        return asignaturaRepository.findByIdStudent(studentList).stream().map(asignatura -> asignatura.AsignaturaTOAsignaturaOutputDto()).collect(Collectors.toList());
    }
}
