package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.LineasFraInputDto;
import com.example.jpacascade.controller.dto.LineasFraOutputDto;

import java.util.List;

public interface LineasFraService {
    LineasFraOutputDto addLinea(LineasFraInputDto lineasFraInputDto);
    void deleteLinea(int id);
    LineasFraOutputDto updateLinea(Integer id, LineasFraInputDto lineasFraInputDto);
    List<LineasFraOutputDto> getAllLinea(int pageNumber, int pageSize);
    LineasFraOutputDto getLinea(Integer id);
}
