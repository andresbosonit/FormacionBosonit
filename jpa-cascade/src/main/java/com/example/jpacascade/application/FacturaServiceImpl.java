package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.*;
import com.example.jpacascade.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaServiceImpl implements FacturaService{
    @Autowired
    CabeceraFraService cabeceraFraService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    LineasFraService lineasFraService;

    @Override
    public FacturaOutputDto addLinea(Integer idFra, LineasFraInputDto lineasFraInputDto) {
        if(idFra != lineasFraInputDto.getCabeceraFraId()){
            throw new EntityNotFoundException("No coinciden los ID de la factura donde se quiere añadir la linea");
        }
        LineasFraOutputDto lineasFraOutputDto = lineasFraService.addLinea(lineasFraInputDto);
        CabeceraFraOutputDto cabeceraFraOutputDto = cabeceraFraService.getCabecera(lineasFraOutputDto.getCabeceraFraId());
        ClienteOutputDto clienteOutputDto = clienteService.getCliente(cabeceraFraOutputDto.getClienteId());
        List<LineasFraOutputDto> lineasFraOutputDtoList = cabeceraFraOutputDto.getLineasFraIdList().stream()
                .map(idLinea -> lineasFraService.getLinea(idLinea)).collect(Collectors.toList());
        return new FacturaOutputDto(cabeceraFraOutputDto, clienteOutputDto, lineasFraOutputDtoList);
    }

    // El metodo para borrar una factura es el mismo que el del servicio cabecera, el cual borra
    // la factura y las lineas en cascada.
    @Override
    public void deleteFactura(int id) {
        cabeceraFraService.deleteCabecera(id);
    }

    // Empleo metodos de los servicios del CRUD, para obtener toda la información de las facturas.
    @Override
    public List<FacturaOutputDto> getAllFacturas(int pageNumber, int pageSize) {
        List<CabeceraFraOutputDto> cabeceraFraOutputDtoList = cabeceraFraService.getAllCabeceras(pageNumber, pageSize);
        return cabeceraFraOutputDtoList.stream()
                .map(cabeceraFraOutputDto -> {
                    ClienteOutputDto clienteOutputDto = clienteService.getCliente(cabeceraFraOutputDto.getClienteId());
                    List<LineasFraOutputDto> lineasFraOutputDtoList = cabeceraFraOutputDto.getLineasFraIdList().stream()
                            .map(idLinea -> lineasFraService.getLinea(idLinea)).collect(Collectors.toList());
                    return new FacturaOutputDto(cabeceraFraOutputDto, clienteOutputDto, lineasFraOutputDtoList);
                }).collect(Collectors.toList());
    }
}
