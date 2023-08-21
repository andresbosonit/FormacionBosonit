package tripbackend.tripbackend.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tripbackend.tripbackend.controller.dtos.input.ViajeInput;
import tripbackend.tripbackend.controller.dtos.output.ClienteOutput;
import tripbackend.tripbackend.controller.dtos.output.ViajeOutput;
import tripbackend.tripbackend.domain.Cliente;
import tripbackend.tripbackend.domain.Viaje;
import tripbackend.tripbackend.exceptions.EntityNotFoundException;
import tripbackend.tripbackend.repository.ClienteRepository;
import tripbackend.tripbackend.repository.ViajeRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ViajeServiceImpl implements ViajeService{
    @Autowired
    ViajeRepository viajeRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public List<Cliente> getClientesFromIds(List<Integer> clientesIds) {
        if(clientesIds != null) {
            Set<Integer> processedIds = new HashSet<>();
            return clientesIds.stream()
                    .filter(clientesId -> processedIds.add(clientesId))
                    .map(clientesId -> clienteRepository.findById(clientesId)
                            .orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente con Id " + clientesId)))
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }
    @Override
    public ViajeOutput addViaje(ViajeInput viajeInput) {
        if(viajeInput.getListaPasajeros().size()<=40){
            List<Cliente> clienteList = getClientesFromIds(viajeInput.getListaPasajeros());
            Viaje viaje = new Viaje(viajeInput);
            viaje.setListaPasajeros(clienteList);
            ViajeOutput viajeOutput = viajeRepository.save(viaje).viajeToViajeOutput();
            clienteList.forEach(cliente -> {
                cliente.getListaViajes().add(viaje);
                clienteRepository.save(cliente);
            });
            return viajeOutput;
        }
        else{
            throw new EntityNotFoundException("El número maximo para añadir pasajeros es de 40");
        }
    }

    @Override
    public void deleteViaje(int id) {
        Viaje viaje = viajeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el viaje con ID: " + id));
        viaje.getListaPasajeros().forEach(cliente -> {
            cliente.getListaViajes().remove(viaje);
            clienteRepository.save(cliente);
        });
        viajeRepository.deleteById(id);
    }

    @Override
    public ViajeOutput updateViaje(Integer id, ViajeInput viajeInput) {
        Viaje viaje = viajeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el viaje con ID: " + id));
        if(viajeInput.getDestino() != null){
            viaje.setDestino(viajeInput.getDestino());
        }
        if(viajeInput.getEstado() != null){
            viaje.setEstado(viajeInput.getEstado());
        }
        if(viajeInput.getHoraLlegada() != null){
            viaje.setHoraLlegada(viajeInput.getHoraLlegada());
        }
        if(viajeInput.getHoraSalida() != null){
            viaje.setHoraSalida(viajeInput.getHoraSalida());
        }
        if(viajeInput.getOrigen() != null){
            viaje.setOrigen(viajeInput.getOrigen());
        }
        if(viajeInput.getListaPasajeros() != null){
            List<Cliente> clienteList = getClientesFromIds(viajeInput.getListaPasajeros());
            viaje.getListaPasajeros().forEach(cliente -> {
                cliente.getListaViajes().remove(viaje);
                clienteRepository.save(cliente);
            });
            viaje.setListaPasajeros(clienteList);
            clienteList.forEach(cliente -> {
                cliente.getListaViajes().add(viaje);
                clienteRepository.save(cliente);
            });
        }
        return viajeRepository.save(viaje).viajeToViajeOutput();
    }

    @Override
    public List<ViajeOutput> getAllViaje(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return viajeRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Viaje::viajeToViajeOutput).toList();
    }

    @Override
    public ViajeOutput getViaje(int id) {
        Viaje Viaje = viajeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el viaje con ID: " + id));
        return Viaje.viajeToViajeOutput();
    }

    // Otros metodos

    @Override
    public ViajeOutput addPasajero(Integer idViaje, Integer idPasajero) {
        Viaje viaje = viajeRepository.findById(idViaje).orElseThrow(() -> new EntityNotFoundException("No se encontró el Viaje con ID: " + idViaje));
        Cliente cliente = clienteRepository.findById(idPasajero).orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente con ID: " + idPasajero));
        if(viaje.getListaPasajeros().contains(cliente)){
            throw new EntityNotFoundException("El pasajero ya tiene pendiente el viaje");
        }else{
            cliente.getListaViajes().add(viaje);
            clienteRepository.save(cliente);
            viaje.getListaPasajeros().add(cliente);
            return viajeRepository.save(viaje).viajeToViajeOutput();
        }
    }

    @Override
    public ViajeOutput modifyEstado(Integer idViaje, String estado) {
        Viaje viaje = viajeRepository.findById(idViaje).orElseThrow(() -> new EntityNotFoundException("No se encontró el Viaje con ID: " + idViaje));
        viaje.setEstado(estado);
        return viajeRepository.save(viaje).viajeToViajeOutput();
    }

    @Override
    public String verifyViaje(Integer idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje).orElseThrow(() -> new EntityNotFoundException("No se encontró el Viaje con ID: " + idViaje));
        if(viaje.getEstado().equals("abierto") && viaje.getListaPasajeros().size()<40){
            return "Disponible";
        }
        return "No disponible";
    }
}
