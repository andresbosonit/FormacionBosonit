package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.CabeceraFraInputDto;
import com.example.jpacascade.controller.dto.CabeceraFraOutputDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CabeceraFraServiceImpl implements CabeceraFraService{
    @Autowired
    LineasFraRepository lineasFraRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    CabeceraFraRepository cabeceraFraRepository;
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
    public CabeceraFraOutputDto addCabecera(CabeceraFraInputDto cabeceraFraInputDto) {
        List<LineasFra> lineasFraList = getLineasFromIds(cabeceraFraInputDto.getLineasFraIdList());
        Cliente cliente = clienteRepository.findById(cabeceraFraInputDto.getClienteId()).orElseThrow(() -> new EntityNotFoundException("No se encontro el cliente con Id " + cabeceraFraInputDto.getClienteId()));
        CabeceraFra cabeceraFra = new CabeceraFra(cabeceraFraInputDto);
        cabeceraFra.setLineasFraList(lineasFraList);
        cabeceraFra.setCliente(cliente);
        return cabeceraFraRepository.save(cabeceraFra).cabeceraFraToCabeceraFraOutputDto();
    }

    @Override
    public void deleteCabecera(int id) {
        CabeceraFra cabeceraFra = cabeceraFraRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la cabecera con ID: " + id); });
        for(LineasFra lineasFra : cabeceraFra.getLineasFraList()){
            lineasFraRepository.delete(lineasFra);
        }
        cabeceraFraRepository.deleteById(id);
    }

    @Override
    public CabeceraFraOutputDto updateCabecera(Integer id, CabeceraFraInputDto cabeceraFraInputDto) {
        CabeceraFra cabeceraFra = cabeceraFraRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la cabecera con ID: " + id); });
        if(cabeceraFraInputDto.getClienteId() != null){
            Cliente cliente = clienteRepository.findById(cabeceraFraInputDto.getClienteId()).orElseThrow(() -> new EntityNotFoundException("No se encontro el cliente con Id " + cabeceraFraInputDto.getClienteId()));
            cabeceraFra.setCliente(cliente);
        }
        if(cabeceraFraInputDto.getImporteFra() != 0){
            cabeceraFra.setImporteFra(cabeceraFraInputDto.getImporteFra());
        }
        if(cabeceraFraInputDto.getLineasFraIdList() != null){
            List<LineasFra> lineasFraList = getLineasFromIds(cabeceraFraInputDto.getLineasFraIdList());
            lineasFraList.forEach(lineasFra -> {
                lineasFra.setCabeceraFra(cabeceraFra);
                lineasFraRepository.save(lineasFra);
            });
        }
        return cabeceraFraRepository.save(cabeceraFra).cabeceraFraToCabeceraFraOutputDto();
    }

    @Override
    public List<CabeceraFraOutputDto> getAllCabeceras(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return cabeceraFraRepository.findAll(pageRequest).getContent()
                .stream()
                .map(CabeceraFra::cabeceraFraToCabeceraFraOutputDto).toList();
    }

    @Override
    public CabeceraFraOutputDto getCabecera(Integer id) {
        CabeceraFra cabeceraFra = cabeceraFraRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró la cabecera con ID: " + id); });
        return cabeceraFra.cabeceraFraToCabeceraFraOutputDto();
    }
}
