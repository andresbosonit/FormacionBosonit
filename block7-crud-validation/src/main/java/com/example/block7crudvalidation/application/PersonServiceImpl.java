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
import jakarta.persistence.criteria.*;
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

        if(conditions.get("usuario") == null) conditions.remove("usuario");
        if(conditions.get("name") == null)  conditions.remove("name");
        if(conditions.get("surname") == null)  conditions.remove("surname");
        if(conditions.get("createdDate") == null) conditions.remove("createdDate");

        conditions.forEach((field, value) -> {
            switch (field) {
                case "usuario", "name", "surname":
                    predicates.add(cb.like(root.get(field),
                            "%" + (String) value + "%"));
                    break;
                case "createdDate":
                    String dateCondition = (String) conditions.get("dateCondition");
                    dateCondition = (dateCondition == null || (!dateCondition.equals(">") && !dateCondition.equals("<") && !dateCondition.equals("="))) ? ">" : dateCondition;
                    switch (dateCondition){
                        case ">":
                            predicates.add(cb.greaterThan(root.get(field),(Date)value));
                            break;
                        case "=":
                            predicates.add(cb.equal(root.get(field),(Date)value));
                            break;
                        case "<":
                            predicates.add(cb.lessThan(root.get(field),(Date)value));
                            break;
                    }
                    break;
            }
        });

        String orderBy = (String) conditions.get("orderBy");
        if(orderBy != null && (orderBy.equals("user") || orderBy.equals("name"))){
            orderBy = orderBy.equals("user") ? "usuario" : orderBy;
            String orderByDirection = (String) conditions.get("orderByDirection");
            orderByDirection = (orderByDirection != null && orderByDirection.equals("desc")) ? "desc" : "asc";
            Order orderByQuery;
            if ("asc".equalsIgnoreCase(orderByDirection)) {
                orderByQuery = cb.asc(root.get(orderBy));
            } else {
                orderByQuery = cb.desc(root.get(orderBy));
            }
            query.orderBy(orderByQuery);
        }

        query.select(root)
                .where(predicates.toArray(new Predicate[predicates.size()]));

        int pageNumber = (int) conditions.get("pageNumber");
        int pageSize = (int) conditions.get("pageSize");
        int firstResult = (pageNumber - 1) * pageSize;
        int maxResults = pageSize;

        return entityManager
                .createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList()
                .stream()
                .map(Person::personToPersonOutputDto)
                .toList();
    }
}
