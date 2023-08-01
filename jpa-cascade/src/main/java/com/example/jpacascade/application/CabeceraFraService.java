package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.CabeceraFraInputDto;
import com.example.jpacascade.controller.dto.CabeceraFraOutputDto;

import java.util.List;

public interface CabeceraFraService {
    CabeceraFraOutputDto addCabecera(CabeceraFraInputDto cabeceraFraInputDto);
    void deleteCabecera(int id);
    CabeceraFraOutputDto updateCabecera(Integer id, CabeceraFraInputDto cabeceraFraInputDto);
    List<CabeceraFraOutputDto> getAllCabeceras(int pageNumber, int pageSize);
    CabeceraFraOutputDto getCabecera(Integer id);
}
