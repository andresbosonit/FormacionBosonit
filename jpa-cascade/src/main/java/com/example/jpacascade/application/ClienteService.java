package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.ClienteInputDto;
import com.example.jpacascade.controller.dto.ClienteOutputDto;
import com.example.jpacascade.domain.Cliente;

import java.util.List;

public interface ClienteService {
    ClienteOutputDto addCliente(ClienteInputDto clienteInputDto);
    void deleteCliente(int id);
    ClienteOutputDto updateCliente(Integer id, ClienteInputDto clienteInputDto);
    List<ClienteOutputDto> getAllCliente(int pageNumber, int pageSize);
    ClienteOutputDto getCliente(Integer id);

    public ClienteOutputDto addClienteV2(Cliente cliente);
}
