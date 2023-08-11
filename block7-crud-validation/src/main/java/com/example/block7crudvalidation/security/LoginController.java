package com.example.block7crudvalidation.security;

import com.example.block7crudvalidation.domain.Person;
import com.example.block7crudvalidation.exceptions.EntityNotFoundException;
import com.example.block7crudvalidation.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class LoginController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    JwtToken jwtToken;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
        String username = requestMap.get("username");
        String password = requestMap.get("password");

        Person person = personRepository.findByUsuario(username)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con nombre de usuario: " + username));

        if (!person.getPassword().equals(password)) {
            throw new EntityNotFoundException("Las contraseñas no coinciden");
        }

        String role = Boolean.TRUE.equals(person.getAdmin()) ? "ROLE_ADMIN" : "ROLE_USER";
        return new ResponseEntity<>(jwtToken.generateToken(username, role), HttpStatus.OK);
    }
}
