package com.example.block6pathvariableheaders;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
public class Controlador {

    // Atendiendo peticiones POST
    @PostMapping(consumes = "application/json")
    public Object devolviendoJSON(@RequestBody Object p){
        return p;
    }

    // Atendiendo peticiones GET
    @GetMapping("user/{id}")
    public String devolviendoID(@PathVariable String id){
        return id;
    }

    // Atendiendo peticion PUT con dos variables
    @PutMapping("post1")
    public HashMap<String,String> devolviendoHashMap(@RequestParam String var1, @RequestParam String var2){
        HashMap<String, String> map = new HashMap<>();
        map.put("var1",var1);
        map.put("var2",var2);
        return map;
    }

    // Atendiendo peticion PUT con infinitas variables
    @PutMapping("post2")
    public HashMap<String,String> devolviendoHashMap(@RequestParam String... vars){
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < vars.length; i++) {
            map.put("var" + (i + 1), vars[i]);
        }
        return map;
    }

    // Peticion get extra
    @GetMapping("header")
    public ResponseEntity<String> mandarHeader(HttpServletRequest request, HttpServletResponse response){
         response.setHeader("h1",request.getHeader("h1"));
         response.setHeader("h2",request.getHeader("h2"));
         return ResponseEntity.ok("Respuesta del método GET con encabezado personalizado");
    }

    // Peticion put extra
    @PostMapping("all")
    public TodosLosDatos devolverTodo(@RequestBody Object body, HttpServletRequest request){
        TodosLosDatos tld = new TodosLosDatos(body, Collections.list(request.getHeaderNames()), Collections.list(request.getParameterNames()));
        return tld;
    }
}
