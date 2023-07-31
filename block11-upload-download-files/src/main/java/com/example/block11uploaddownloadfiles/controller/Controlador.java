package com.example.block11uploaddownloadfiles.controller;

import com.example.block11uploaddownloadfiles.application.FicheroService;
import com.example.block11uploaddownloadfiles.domain.Fichero;
import com.example.block11uploaddownloadfiles.repository.FicheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class Controlador {

    @Autowired
    FicheroService ficheroService;
    @PostMapping("/upload/{tipo}")
    public ResponseEntity<?> subirFichero(@PathVariable String tipo,
                                          @RequestParam("file") MultipartFile file) throws IOException{
        return ficheroService.subirFichero(tipo,file);
    }

    @GetMapping("/setpath")
    public ResponseEntity<String> cambiarPath(@RequestParam("path") String ruta){
        return ficheroService.modificarRuta(ruta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> devolverFicheroId(@PathVariable int id){
        return ficheroService.descargarFicheroId(id);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> devolverFicheroNombre(@PathVariable String nombre){
        return ficheroService.descargarFicheroNombre(nombre);
    }
}
