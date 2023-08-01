package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.ClienteInputDto;
import com.example.jpacascade.controller.dto.ClienteOutputDto;
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
public class ClienteServiceImpl implements ClienteService{
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    CabeceraFraRepository cabeceraFraRepository;
    @Autowired
    CabeceraFraService cabeceraFraService;
    private List<CabeceraFra> getLineasFromIds(List<Integer> cabecerasIds) {
        if(cabecerasIds != null) {
            return cabecerasIds.stream()
                    .map(cabeceraId -> cabeceraFraRepository.findById(cabeceraId)
                            .orElseThrow(() -> new EntityNotFoundException("No se encontró la cabecera con Id " + cabeceraId)))
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }
    @Override
    public ClienteOutputDto addCliente(ClienteInputDto clienteInputDto) {
        List<CabeceraFra> cabeceraFraList = getLineasFromIds(clienteInputDto.getCabeceraFraIdList());
        Cliente cliente = new Cliente(clienteInputDto);
        cliente.setCabeceraFraList(cabeceraFraList);
        return clienteRepository.save(cliente).clienteToClienteOutputDto();
    }

    @Override
    public void deleteCliente(int id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró el cliente con ID: " + id); });
        for(CabeceraFra cabeceraFra : cliente.getCabeceraFraList()){
            cabeceraFraService.deleteCabecera(cabeceraFra.getIdCabeceraFra());
        }
        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteOutputDto updateCliente(Integer id, ClienteInputDto clienteInputDto) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró el cliente con ID: " + id); });
        if(clienteInputDto.getNombre() != null){
            cliente.setNombre(clienteInputDto.getNombre());
        }
        if(clienteInputDto.getCabeceraFraIdList() != null){
            List<CabeceraFra> cabeceraFraList = getLineasFromIds(clienteInputDto.getCabeceraFraIdList());
            cabeceraFraList.forEach(cabeceraFra -> {
                cabeceraFra.setCliente(cliente);
                cabeceraFraRepository.save(cabeceraFra);
            });
        }
        return clienteRepository.save(cliente).clienteToClienteOutputDto();
    }

    @Override
    public List<ClienteOutputDto> getAllCliente(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return clienteRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Cliente::clienteToClienteOutputDto).toList();
    }

    @Override
    public ClienteOutputDto getCliente(Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró el cliente con ID: " + id); });
        return cliente.clienteToClienteOutputDto();
    }
}
