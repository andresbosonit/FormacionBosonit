package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.controller.dto.*;
import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.domain.Profesor;
import com.example.block7crudvalidation.domain.Student;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.exceptions.UnprocessableEntityException;
import com.example.block7crudvalidation.repository.PersonRepository;

import com.example.block7crudvalidation.repository.ProfesorRepository;
import com.example.block7crudvalidation.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    PersonRepository personRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    ProfesorService profesorService;
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public PersonOutputDto addPerson(PersonInputDto person) throws UnprocessableEntityException {
        List<String> mensajes = new ArrayList<>();
        if (person.getUsuario()==null) {mensajes.add("Usuario no puede ser nulo");}
        else if (person.getUsuario().length()>10) {mensajes.add("Longitud de usuario no puede ser superior a 10 caracteres");}
        else if (person.getUsuario().length()<6) {mensajes.add("Longitud de usuario no puede ser inferior a 6 caracteres");}
        if (person.getPassword() == null) {mensajes.add("Contraseña no puede ser nulo");}
        if (person.getName() == null) {mensajes.add("Nombre no puede ser nulo");}
        if (person.getCompanyEmail() == null) {mensajes.add("Email de la compañia no puede ser nulo");}
        if (person.getPersonalEmail() == null) {mensajes.add("Email personal no puede ser nulo");}
        if (person.getCity() == null) {mensajes.add("Ciudad no puede ser nulo");}
        if (person.getActive() == null) {mensajes.add("Activo no puede ser nulo");}
        if (person.getCreatedDate() == null) {mensajes.add("Fecha de creacion no puede ser nulo");}
        if(mensajes.isEmpty()){
            return personRepository.save(new Person(person)).personToPersonOutputDto();
        }else{
            throw new UnprocessableEntityException(mensajes.toString());
        }
    }

    @Override
    public void deletePersonId(int id) throws EntityNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID: " + id));
        studentRepository.findByPersona(person).ifPresent(student -> studentService.deleteStudentId(student.getIdStudent()));
        profesorRepository.findByPersona(person).ifPresent(profesor -> profesorService.deleteProfesorId(profesor.getIdProfesor()));
        personRepository.deleteById(id);
    }

    public Optional<Student> getStudent(int id){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID: " + id));
        return studentRepository.findByPersona(person);
    }

    public Optional<Profesor> getProfesor(int id){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID: " + id));
        return profesorRepository.findByPersona(person);
    }

    @Override
    public PersonOutputDto updatePerson(Integer idPersona, PersonInputDto person) {
        Person persona = personRepository.findById(idPersona).orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID: " + idPersona));
        persona.setUsuario(Objects.requireNonNullElse(person.getUsuario(), persona.getUsuario()));
        persona.setPassword(Objects.requireNonNullElse(person.getPassword(), persona.getPassword()));
        persona.setName(Objects.requireNonNullElse(person.getName(), persona.getName()));
        if(person.getSurname() != null){
            persona.setSurname(person.getSurname());
        }
        persona.setCompanyEmail(Objects.requireNonNullElse(person.getCompanyEmail(), persona.getCompanyEmail()));
        persona.setPersonalEmail(Objects.requireNonNullElse(person.getPersonalEmail(), persona.getPersonalEmail()));
        persona.setCity(Objects.requireNonNullElse(person.getCity(), persona.getCity()));
        persona.setActive(Objects.requireNonNullElse(person.getActive(), persona.getActive()));
        persona.setCreatedDate(Objects.requireNonNullElse(person.getCreatedDate(), persona.getCreatedDate()));
        if(person.getImageUrl() != null){
            persona.setImageUrl(person.getImageUrl());
        }
        if(person.getTerminationDate() != null){
            persona.setTerminationDate(person.getTerminationDate());
        }
        List<String> mensajes = new ArrayList<>();
        if (persona.getUsuario().length()>10) {mensajes.add("Longitud de usuario no puede ser superior a 10 caracteres");}
        else if (persona.getUsuario().length()<6) {mensajes.add("Longitud de usuario no puede ser inferior a 6 caracteres");}
        if(mensajes.isEmpty()){
            return personRepository.save(persona).personToPersonOutputDto();
        }else{
            throw new UnprocessableEntityException(mensajes.toString());
        }
    }

    @Override
    public List<PersonOutputDto> getAllPersons(int pageNumber, int pageSize, String output) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personRepository.findAll(pageRequest).getContent()
                .stream()
                .map(person -> getPersonOutputDto(person.getIdPersona(), output)).toList();
    }

    @Override
    public PersonOutputDto getPersonOutputDto(int id, String output) {
        PersonOutputDto personOutputDto = getPerson(id);
        if (output.equals("full")) {
            StudentOutputDto studentOutputDto = getStudent(id).map(Student::studentToStudentOutputDto).orElse(null);
            if (studentOutputDto != null) {
                return new PersonStudentOutputFullDto(personOutputDto, studentOutputDto);
            }
            ProfesorOutputDto profesorOutputDto = getProfesor(id).map(Profesor::profesorToProfesorOutputDto).orElse(null);
            if (profesorOutputDto != null) {
                return new PersonProfesorOutputFullDto(personOutputDto, profesorOutputDto);
            }
        }
        return personOutputDto;
    }

    @Override
    public PersonOutputDto getPerson(int id){
        Person persona = personRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la persona con ID: " + id); });
        return persona.personToPersonOutputDto();
    }

    @Override
    public List<PersonOutputDto> getPersonsName(int pageNumber, int pageSize, String name, String output) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Person> personas = personRepository.findByName(name, pageRequest);
        return personas.stream()
                .map(person -> getPersonOutputDto(person.getIdPersona(), output))
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonOutputDto> getCustomQuery(HashMap<String, Object> conditions) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = cb.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);

        List<Predicate> predicates = new ArrayList<>();

        /*conditions.forEach((field, value) -> {
            switch (field) {
                case "name", "lastName", "surname":
                    predicates.add(cb.like(root.get(field),
                            "%" + (String) value + "%"));
                    break;
                case "fechaCreacionSuperior":
                    predicates.add(cb.greaterThan(root.get("fechaCreacion"), (Date) value));
                    break;
                case "fechaCreacionInferior":
                    predicates.add(cb.lessThan(root.get("fechaCreacion"), (Date) value));
                    break;

            }
        });

        String orderByField = (String) conditions.get("orderBy");
        String orderDirection = (String) conditions.get("orderDirection");

        if (orderByField != null && orderDirection != null) {
            if (orderDirection.equalsIgnoreCase("asc")) {
                query.orderBy(cb.asc(root.get(orderByField)));
            } else if (orderDirection.equalsIgnoreCase("desc")) {
                query.orderBy(cb.desc(root.get(orderByField)));
            }
        }*/
        conditions.forEach((field, value) -> {
            switch (field) {
                case "name", "lastName", "surname":
                    predicates.add(cb.like(root.get(field),
                            "%" + (String) value + "%"));
                    break;
            }
        });
        query.select(root)
                .where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager
                .createQuery(query)
                .getResultList()
                .stream()
                .map(Person::personToPersonOutputDto)
                .toList();
    }
}
