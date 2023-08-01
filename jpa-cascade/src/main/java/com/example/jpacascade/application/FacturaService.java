package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.FacturaOutputDto;
import com.example.jpacascade.controller.dto.LineasFraInputDto;

import java.util.List;

public interface FacturaService {
    FacturaOutputDto addLinea(Integer idFra, LineasFraInputDto lineasFraInputDto);
    void deleteFactura(int id);
    List<FacturaOutputDto> getAllFacturas(int pageNumber, int pageSize);
}
