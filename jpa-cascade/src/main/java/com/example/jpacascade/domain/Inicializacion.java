package com.example.jpacascade.domain;

import com.example.jpacascade.application.FacturaService;
import com.example.jpacascade.application.ClienteService;
import com.example.jpacascade.application.LineasFraService;
import com.example.jpacascade.controller.dto.ClienteInputDto;
import com.example.jpacascade.controller.dto.LineasFraInputDto;
import com.example.jpacascade.repository.ClienteRepository;
import com.example.jpacascade.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Inicializacion implements CommandLineRunner {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FacturaRepository facturaRepository;
    @Autowired
    FacturaService facturaService;

    @Autowired
    LineasFraService lineasFraService;

    @Override
    public void run(String... args){
        Cliente cliente = new Cliente(new ClienteInputDto("Andres"));
        clienteRepository.save(cliente);

        Factura factura = new Factura(1, cliente, new ArrayList<>(),0);
        LineasFra linea1 = new LineasFra(1, "linea1", 1, 1, factura);
        LineasFra linea2 = new LineasFra(2, "linea2", 2, 2, factura);
        List<LineasFra> lista = new ArrayList<>();

        lista.add(linea1);
        lista.add(linea2);

        factura.setLineasFraList(lista);

        facturaRepository.save(factura);
    }
}
