package com.example.block11uploaddownloadfiles.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fichero {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Date fechaSubida;
    @Column(nullable = false)
    private String categoria;

}
