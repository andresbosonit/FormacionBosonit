package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.*;
import com.example.jpacascade.domain.Factura;
import com.example.jpacascade.domain.Cliente;
import com.example.jpacascade.domain.LineasFra;
import com.example.jpacascade.exceptions.EntityNotFoundException;
import com.example.jpacascade.repository.FacturaRepository;
import com.example.jpacascade.repository.ClienteRepository;
import com.example.jpacascade.repository.LineasFraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaServiceImpl implements FacturaService {
    @Autowired
    LineasFraRepository lineasFraRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    FacturaRepository FacturaRepository;

    @Autowired
    LineasFraService lineasFraService;
    private List<LineasFra> getLineasFromIds(List<Integer> lineasIds) {
        if(lineasIds != null) {
            return lineasIds.stream()
                    .map(lineaId -> lineasFraRepository.findById(lineaId)
                            .orElseThrow(() -> new EntityNotFoundException("No se encontró la linea con Id " + lineaId)))
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }
    @Override
    public FacturaOutputDto addFactura(FacturaInputDto facturaInputDto) {
        List<LineasFra> lineasList = getLineasFromIds(facturaInputDto.getLineasFraIdList());
        Cliente cliente = clienteRepository.findById(facturaInputDto.getClienteId()).orElseThrow(() -> new EntityNotFoundException("No se encontro el cliente con Id " + facturaInputDto.getClienteId()));
        Factura factura = new Factura(facturaInputDto);
        factura.setLineasFraList(lineasList);
        factura.setCliente(cliente);
        return FacturaRepository.save(factura).facturaToFacturaOutputDto();
    }

    @Override
    public void deleteFactura(int id) {
        FacturaRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la Factura con ID: " + id); });
        FacturaRepository.deleteById(id);
    }

    @Override
    public FacturaOutputDto updateFactura(Integer id, FacturaInputDto facturaInputDto) {
        Factura factura = FacturaRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la Factura con ID: " + id); });
        if(facturaInputDto.getImporteFra() != 0){
            factura.setImporteFra(facturaInputDto.getImporteFra());
        }
        return FacturaRepository.save(factura).facturaToFacturaOutputDto();
    }

    @Override
    public List<FacturaOutputDto> getAllFacturas(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return FacturaRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Factura::facturaToFacturaOutputDto).toList();
    }

    @Override
    public FacturaOutputDto getFactura(Integer id) {
        Factura factura = FacturaRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la Factura con ID: " + id); });
        return factura.facturaToFacturaOutputDto();
    }

    @Override
    public FacturaOutputDto addLinea(Integer idFra, LineasFraInputDto lineasFraInputDto) {
        if(idFra != lineasFraInputDto.getFacturaId()){
            throw new EntityNotFoundException("No coinciden los ID de la factura donde se quiere añadir la linea");
        }
        lineasFraService.addLinea(lineasFraInputDto);
        FacturaOutputDto factura = FacturaRepository.findById(idFra).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la Factura con ID: " + idFra); }).facturaToFacturaOutputDto();
        return factura;
    }

    public FacturaOutputDto addFacturaV2(Factura factura) {
        return FacturaRepository.save(factura).facturaToFacturaOutputDto();
    }
}
