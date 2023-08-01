package com.example.jpacascade.application;

import com.example.jpacascade.controller.dto.ClienteInputDto;
import com.example.jpacascade.controller.dto.ClienteOutputDto;
import com.example.jpacascade.domain.Factura;
import com.example.jpacascade.domain.Cliente;
import com.example.jpacascade.exceptions.EntityNotFoundException;
import com.example.jpacascade.repository.FacturaRepository;
import com.example.jpacascade.repository.ClienteRepository;
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
    FacturaRepository facturaRepository;
    @Autowired
    FacturaService facturaService;
    private List<Factura> getLineasFromIds(List<Integer> cabecerasIds) {
        if(cabecerasIds != null) {
            return cabecerasIds.stream()
                    .map(cabeceraId -> facturaRepository.findById(cabeceraId)
                            .orElseThrow(() -> new EntityNotFoundException("No se encontró la cabecera con Id " + cabeceraId)))
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }
    @Override
    public ClienteOutputDto addCliente(ClienteInputDto clienteInputDto) {
        List<Factura> facturaList = getLineasFromIds(clienteInputDto.getFacturaIdList());
        Cliente cliente = new Cliente(clienteInputDto);
        cliente.setFacturaList(facturaList);
        return clienteRepository.save(cliente).clienteToClienteOutputDto();
    }

    @Override
    public void deleteCliente(int id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró el cliente con ID: " + id); });
        for(Factura factura : cliente.getFacturaList()){
            facturaService.deleteFactura(factura.getIdFactura());
        }
        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteOutputDto updateCliente(Integer id, ClienteInputDto clienteInputDto) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No se encontró el cliente con ID: " + id); });
        if(clienteInputDto.getNombre() != null){
            cliente.setNombre(clienteInputDto.getNombre());
        }
        if(clienteInputDto.getFacturaIdList() != null){
            List<Factura> facturaList = getLineasFromIds(clienteInputDto.getFacturaIdList());
            facturaList.forEach(Factura -> {
                Factura.setCliente(cliente);
                facturaRepository.save(Factura);
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

    public ClienteOutputDto addClienteV2(Cliente cliente) {
        return clienteRepository.save(cliente).clienteToClienteOutputDto();
    }
}
