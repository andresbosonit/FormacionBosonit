package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.LineasFraInputDto;
import com.example.jpacascade.controller.dto.LineasFraOutputDto;
import com.example.jpacascade.domain.Factura;
import com.example.jpacascade.domain.LineasFra;
import com.example.jpacascade.exceptions.EntityNotFoundException;
import com.example.jpacascade.repository.FacturaRepository;
import com.example.jpacascade.repository.LineasFraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineasFraServiceImpl implements LineasFraService{
    @Autowired
    LineasFraRepository lineasFraRepository;
    @Autowired
    FacturaRepository facturaRepository;
    @Override
    public LineasFraOutputDto addLinea(LineasFraInputDto lineasFraInputDto) {
        Factura factura = facturaRepository.findById(lineasFraInputDto.getFacturaId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la Factura con Id " + lineasFraInputDto.getFacturaId()));
        LineasFra lineasFra = new LineasFra(lineasFraInputDto);
        lineasFra.setFactura(factura);
        return lineasFraRepository.save(lineasFra).lineasFraToLineasFraOutputDto();
    }

    @Override
    public void deleteLinea(int id) {
        lineasFraRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la linea con Id " + id));
        lineasFraRepository.deleteById(id);
    }

    @Override
    public LineasFraOutputDto updateLinea(Integer id, LineasFraInputDto lineasFraInputDto) {
        LineasFra lineasFra = lineasFraRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la linea con Id " + id));
        if(lineasFraInputDto.getCantidad() != 0){
            lineasFra.setCantidad(lineasFraInputDto.getCantidad());
        }
        if(lineasFraInputDto.getPrecio() != 0){
            lineasFra.setPrecio(lineasFraInputDto.getPrecio());
        }
        if(lineasFraInputDto.getProNomb() != null){
            lineasFra.setProNomb(lineasFraInputDto.getProNomb());
        }
        return lineasFraRepository.save(lineasFra).lineasFraToLineasFraOutputDto();
    }

    @Override
    public List<LineasFraOutputDto> getAllLinea(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return lineasFraRepository.findAll(pageRequest).getContent()
                .stream()
                .map(LineasFra::lineasFraToLineasFraOutputDto).toList();
    }

    @Override
    public LineasFraOutputDto getLinea(Integer id) {
        LineasFra lineasFra = lineasFraRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la linea con Id " + id));
        return lineasFra.lineasFraToLineasFraOutputDto();
    }
}
