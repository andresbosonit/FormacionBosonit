package com.example.block7crudvalidation.controller.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonOutputDto {
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

    public PersonOutputDto(PersonOutputDto personOutputDto) {
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
