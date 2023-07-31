package com.example.block11uploaddownloadfiles.application;

import com.example.block11uploaddownloadfiles.domain.Fichero;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FicheroService {
    ResponseEntity<?> subirFichero(String tipo, MultipartFile file) throws IOException;
    ResponseEntity<String> modificarRuta(String ruta);
    ResponseEntity<?> descargarFicheroId(int id);
    ResponseEntity<?> descargarFicheroNombre(String nombre);
}
