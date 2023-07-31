package com.example.block11uploaddownloadfiles.application;

import com.example.block11uploaddownloadfiles.domain.Fichero;
import com.example.block11uploaddownloadfiles.repository.FicheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@Service
public class FicheroServiceImpl implements FicheroService{
    private String ruta = "downloads";
    @Autowired
    FicheroRepository ficheroRepository;
    @Override
    public ResponseEntity<?> subirFichero(String tipo, MultipartFile file) throws IOException {
        if (!file.getOriginalFilename().endsWith(tipo)) {
            String mensajeError = "El fichero debe tener extensión " + tipo;
            return ResponseEntity.badRequest().body(mensajeError);
        }
        if(this.ruta == null){
            String mensajeError = "El path no esta definido";
            return ResponseEntity.badRequest().body(mensajeError);
        }
        Path path = Paths.get(ruta);
        if (!Files.isDirectory(path)) {
            String mensajeError = "El path: " + path + " no es un directorio";
            return ResponseEntity.badRequest().body(mensajeError);
        }
        String nombreFichero = file.getOriginalFilename();
        Path filePath = Paths.get(this.ruta).resolve(nombreFichero);
        Files.write(filePath, file.getBytes());

        Fichero fichero = new Fichero();
        fichero.setNombre(nombreFichero);
        fichero.setFechaSubida(new Date());
        fichero.setCategoria(tipo);
        fichero = ficheroRepository.save(fichero);
        return ResponseEntity.ok(fichero);
    }

    @Override
    public ResponseEntity<String> modificarRuta(String ruta) {
        Path path = Paths.get(ruta);
        if(!Files.exists(path)){
            String mensajeError = "El path: " + path + " no existe";
            return ResponseEntity.badRequest().body(mensajeError);
        }
        this.ruta = ruta;
        return ResponseEntity.ok("El path ha cambiado a ser: " + this.ruta);
    }

    @Override
    public ResponseEntity<?> descargarFicheroId(int id) {
        Optional<Fichero> posiblefichero = ficheroRepository.findById(id);
        if(!posiblefichero.isPresent()) {
            return ResponseEntity.badRequest().body("No se ha encontrado el fichero con ID: " + id);
        }
        return descargarFichero(posiblefichero.get());
    }

    @Override
    public ResponseEntity<?> descargarFicheroNombre(String nombre) {
        Optional<Fichero> posiblefichero = ficheroRepository.findByNombre(nombre);
        if(!posiblefichero.isPresent()) {
            return ResponseEntity.badRequest().body("No se ha encontrado el fichero con nombre: " + nombre);
        }
        return descargarFichero(posiblefichero.get());
    }

    private ResponseEntity<?> descargarFichero(Fichero fichero){
        String rutaFichero = this.ruta + "/" + fichero.getNombre();

        Path path = Paths.get(rutaFichero);
        if (!Files.exists(path) || Files.isDirectory(path)) {
            return ResponseEntity.badRequest().body("El fichero no se encuentra disponible en la ruta especificada.");
        }
        try {
            byte[] contenido = Files.readAllBytes(path);

            String nombreFichero = "Copia-"+fichero.getNombre();
            Path filePath = Paths.get(this.ruta).resolve(nombreFichero);
            Files.write(filePath, contenido);

            Fichero ficheroCopia = new Fichero();
            ficheroCopia.setNombre(nombreFichero);
            ficheroCopia.setFechaSubida(new Date());
            ficheroCopia.setCategoria(fichero.getCategoria());
            ficheroCopia = ficheroRepository.save(ficheroCopia);

            return ResponseEntity.ok(ficheroCopia);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al leer el contenido del fichero.");
        }
    }
}
