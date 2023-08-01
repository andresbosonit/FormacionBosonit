package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.LineasFraInputDto;
import com.example.jpacascade.controller.dto.LineasFraOutputDto;
import com.example.jpacascade.domain.CabeceraFra;
import com.example.jpacascade.domain.Cliente;
import com.example.jpacascade.domain.LineasFra;
import com.example.jpacascade.exceptions.EntityNotFoundException;
import com.example.jpacascade.repository.CabeceraFraRepository;
import com.example.jpacascade.repository.ClienteRepository;
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
    CabeceraFraRepository cabeceraFraRepository;
    @Override
    public LineasFraOutputDto addLinea(LineasFraInputDto lineasFraInputDto) {
        CabeceraFra cabeceraFra = cabeceraFraRepository.findById(lineasFraInputDto.getCabeceraFraId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la cabecera con Id " + lineasFraInputDto.getCabeceraFraId()));
        LineasFra lineasFra = new LineasFra(lineasFraInputDto);
        lineasFra.setCabeceraFra(cabeceraFra);
        return lineasFraRepository.save(lineasFra).lineasFraToLineasFraOutputDto();
    }

    @Override
    public void deleteLinea(int id) {
        LineasFra lineasFra = lineasFraRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la linea con Id " + id));
        CabeceraFra cabeceraFra = cabeceraFraRepository.findById(lineasFra.getCabeceraFra().getIdCabeceraFra()).orElseThrow(() -> new EntityNotFoundException("No se encontró la cabecera con Id " + lineasFra.getCabeceraFra().getIdCabeceraFra()));
        cabeceraFra.getLineasFraList().remove(lineasFra);
        cabeceraFraRepository.save(cabeceraFra);
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
        if(lineasFraInputDto.getCabeceraFraId() != null){
            CabeceraFra cabeceraFra = cabeceraFraRepository.findById(lineasFraInputDto.getCabeceraFraId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la cabecera con Id " + lineasFraInputDto.getCabeceraFraId()));
            lineasFra.setCabeceraFra(cabeceraFra);
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
