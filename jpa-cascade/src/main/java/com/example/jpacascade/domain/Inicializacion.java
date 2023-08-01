package com.example.jpacascade.domain;

import com.example.jpacascade.application.CabeceraFraService;
import com.example.jpacascade.application.ClienteService;
import com.example.jpacascade.controller.dto.CabeceraFraInputDto;
import com.example.jpacascade.controller.dto.ClienteInputDto;
import com.example.jpacascade.controller.dto.ClienteOutputDto;
import com.example.jpacascade.controller.dto.LineasFraInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Inicializacion implements CommandLineRunner {
    @Autowired
    ClienteService clienteService;

    @Autowired
    CabeceraFraService cabeceraFraService;

    @Override
    public void run(String... args){
        // Crear y guardar un nuevo cliente
        /*ClienteInputDto cliente = new ClienteInputDto();
        cliente.setCabeceraFraIdList(new ArrayList<>());
        cliente.setNombre("Nuevo Cliente");
        clienteService.addCliente(cliente);

        // Crear una factura y dos líneas de factura
        CabeceraFraInputDto factura = new CabeceraFraInputDto();
        LineasFra linea1 = new LineasFra();
        LineasFra linea2 = new LineasFra();
        linea1.setProNomb("Producto 1");
        linea2.setProNomb("Producto 2");
        factura.setLineasFraIdList(new ArrayList<>());
        factura.getLineasFraIdList().add(linea1);
        factura.getLineasFraIdList().add(linea2);

        cabeceraFraService.addCabecera()

        // Asignar la factura al cliente
        cliente.getCabeceraFraIdList().add(factura.getIdCabeceraFra());

        // Agregar las líneas de factura a la factura


        // Guardar el cliente (esto guardará la factura y las líneas de factura también debido a las relaciones y cascadas configuradas)
        clienteService.addCliente(cliente);*/
    }
}
