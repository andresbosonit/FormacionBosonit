package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.EstudianteAsignaturaInputDto;
import com.example.block7crudvalidation.controller.dto.EstudianteAsignaturaOutputDto;
import com.example.block7crudvalidation.domain.EstudianteAsignatura;
import com.example.block7crudvalidation.domain.Profesor;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.repository.EstudianteAsignaturaRepository;
import com.example.block7crudvalidation.repository.ProfesorRepository;
import com.example.block7crudvalidation.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EstudianteAsignaturaServiceImpl implements EstudianteAsignaturaService{
    @Autowired
    EstudianteAsignaturaRepository estudianteAsignaturaRepository;

    @Autowired
    StudentRepository studentRepository;
    @Override
    public EstudianteAsignaturaOutputDto addEstudianteAsignatura(EstudianteAsignaturaInputDto estudianteAsignaturaInputDto) {
        Student estudiante = studentRepository.findById(estudianteAsignaturaInputDto.getIdStudent()).orElseThrow(() -> new EntityNotFoundException("No se encontro el estudiante con Id " + estudianteAsignaturaInputDto.getIdStudent()));
        EstudianteAsignatura estudianteAsignatura = new EstudianteAsignatura(estudianteAsignaturaInputDto);
        estudianteAsignatura.setIdStudent(estudiante);
        return estudianteAsignaturaRepository.save(estudianteAsignatura).estudianteAsignaturaTOEstudianteAsignaturaOutputDto();
    }

    @Override
    public void deleteEstudianteAsignaturaId(int id) {
        Optional<EstudianteAsignatura> posibleEstudianteAsignatura = estudianteAsignaturaRepository.findById(id);
        if(!posibleEstudianteAsignatura.isPresent()) {throw new EntityNotFoundException("No se encontró el EstudianteAsignatura con ID: " + id); }
        estudianteAsignaturaRepository.deleteById(id);
    }

    @Override
    public EstudianteAsignaturaOutputDto updateEstudianteAsignatura(EstudianteAsignaturaInputDto estudianteAsignatura) {
        Optional<EstudianteAsignatura> posibleEstudianteAsignatura = estudianteAsignaturaRepository.findById(estudianteAsignatura.getIdAsignatura());
        if(!posibleEstudianteAsignatura.isPresent()) {throw new EntityNotFoundException("No se encontró el EstudianteAsignatura con ID: " + estudianteAsignatura.getIdAsignatura()); }
        estudianteAsignatura.setIdAsignatura(Objects.requireNonNullElse(estudianteAsignatura.getIdAsignatura(), posibleEstudianteAsignatura.get().getIdAsignatura()));
        //estudianteAsignatura.setIdStudent(Objects.requireNonNullElse(estudianteAsignatura.getIdStudent(), posibleEstudianteAsignatura.get().getIdStudent()));
        estudianteAsignatura.setInitialDate(Objects.requireNonNullElse(estudianteAsignatura.getInitialDate(), posibleEstudianteAsignatura.get().getInitialDate()));
        return estudianteAsignaturaRepository.save(new EstudianteAsignatura(estudianteAsignatura)).estudianteAsignaturaTOEstudianteAsignaturaOutputDto();
    }

    @Override
    public List<EstudianteAsignaturaOutputDto> getAllEstudianteAsignatura(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return estudianteAsignaturaRepository.findAll(pageRequest).getContent()
                .stream()
                .map(EstudianteAsignatura::estudianteAsignaturaTOEstudianteAsignaturaOutputDto).toList();
    }

    @Override
    public EstudianteAsignaturaOutputDto getEstudianteAsignatura(int id) {
        Optional<EstudianteAsignatura> posibleEstudianteAsignatura = estudianteAsignaturaRepository.findById(id);
        if(!posibleEstudianteAsignatura.isPresent()) {throw new EntityNotFoundException("No se encontró el EstudianteAsignatura con ID: " + id); }
        return estudianteAsignaturaRepository.getReferenceById(id).estudianteAsignaturaTOEstudianteAsignaturaOutputDto();
    }
}
