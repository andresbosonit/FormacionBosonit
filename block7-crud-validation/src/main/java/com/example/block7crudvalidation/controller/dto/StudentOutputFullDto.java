package com.example.block7crudvalidation.controller.dto;

import com.example.block7crudvalidation.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data
public class StudentOutputFullDto extends StudentOutputDto{
    private int idPersona;
    private String usuario;
    private String password;
    private String name;
    private String surname;
    private String companyEmail;
    private String personalEmail;
    private String city;
    private Boolean active;
    private Date createdDate;
    private String imageUrl;
    private Date terminationDate;
    public StudentOutputFullDto(StudentOutputDto studentOutputDto, PersonOutputDto personOutputDto){
        super(studentOutputDto);
        this.idPersona = personOutputDto.getIdPersona();
        this.usuario = personOutputDto.getUsuario();
        this.password = personOutputDto.getPassword();
        this.name = personOutputDto.getName();
        this.surname = personOutputDto.getSurname();
        this.companyEmail = personOutputDto.getCompanyEmail();
        this.personalEmail = personOutputDto.getPersonalEmail();
        this.city = personOutputDto.getCity();
        this.active = personOutputDto.getActive();
        this.createdDate = personOutputDto.getCreatedDate();
        this.imageUrl = personOutputDto.getImageUrl();
        this.terminationDate = personOutputDto.getTerminationDate();
    }
}
