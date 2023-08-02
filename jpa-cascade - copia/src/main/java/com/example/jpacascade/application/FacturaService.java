package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.FacturaInputDto;
import com.example.jpacascade.controller.dto.FacturaOutputDto;
import com.example.jpacascade.controller.dto.LineasFraInputDto;
import com.example.jpacascade.domain.Factura;

import java.util.List;

public interface FacturaService {
    FacturaOutputDto addFactura(FacturaInputDto facturaInputDto);
    void deleteFactura(int id);
    FacturaOutputDto updateFactura(Integer id, FacturaInputDto facturaInputDto);
    List<FacturaOutputDto> getAllFacturas(int pageNumber, int pageSize);
    FacturaOutputDto getFactura(Integer id);
    FacturaOutputDto addLinea(Integer idFra, LineasFraInputDto lineasFraInputDto);
    FacturaOutputDto addFacturaV2(Factura factura);
}
