package com.example.block10dockerizeapp.domain;


import com.example.block10dockerizeapp.controller.dto.PersonInputDto;
import com.example.block10dockerizeapp.controller.dto.PersonOutputDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    @Column(name = "id_persona")
    private int idPersona;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "company_email")
    private String companyEmail;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "city")
    private String city;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "imagen_url")
    private String imageUrl;

    @Column(name = "termination_date")
    @Temporal(TemporalType.DATE)
    private Date terminationDate;

    public Person(PersonInputDto personInputDto){
        this.usuario = personInputDto.getUsuario();
        this.password = personInputDto.getPassword();
        this.name = personInputDto.getName();
        this.surname = personInputDto.getSurname();
        this.companyEmail = personInputDto.getCompanyEmail();
        this.personalEmail = personInputDto.getPersonalEmail();
        this.city = personInputDto.getCity();
        this.active = personInputDto.getActive();
        this.createdDate = personInputDto.getCreatedDate();
        this.imageUrl = personInputDto.getImageUrl();
        this.terminationDate = personInputDto.getTerminationDate();
    }

    public PersonOutputDto personToPersonOutputDto(){
        return new PersonOutputDto(this.idPersona,
        this.usuario,
        this.password,
        this.name,
        this.surname,
        this.companyEmail,
        this.personalEmail,
        this.city,
        this.active,
        this.createdDate,
        this.imageUrl,
        this.terminationDate);
    }
}
