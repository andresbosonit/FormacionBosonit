package tripbackend.tripbackend.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tripbackend.tripbackend.controller.dtos.input.ClienteInput;
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
public class ClienteServiceImpl implements ClienteService{
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ViajeRepository viajeRepository;

    public List<Viaje> getViajesFromIds(List<Integer> viajesIds) {
        if(viajesIds != null) {
            Set<Integer> processedIds = new HashSet<>();
            return viajesIds.stream()
                    .filter(viajeId -> processedIds.add(viajeId))
                    .map(viajeId -> viajeRepository.findById(viajeId)
                            .orElseThrow(() -> new EntityNotFoundException("No se encontró el viaje con Id " + viajeId)))
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }
    @Override
    public ClienteOutput addCliente(ClienteInput clienteInput) {
        List<Viaje> viajeList = getViajesFromIds(clienteInput.getListaViajes());
        Cliente cliente = new Cliente(clienteInput);
        cliente.setListaViajes(viajeList);
        ClienteOutput clienteOutput = clienteRepository.save(cliente).clienteToClienteOutput();
        viajeList.forEach(viaje -> {
            viaje.getListaPasajeros().add(cliente);
            viajeRepository.save(viaje);
        });
        return clienteOutput;
    }

    @Override
    public void deleteCliente(int id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente con ID: " + id));
        cliente.getListaViajes().forEach(viaje -> {
            viaje.getListaPasajeros().remove(cliente);
            viajeRepository.save(viaje);
        });
        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteOutput updateCliente(Integer id, ClienteInput clienteInput) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente con ID: " + id));
        if(clienteInput.getNombre() != null){
            cliente.setNombre(clienteInput.getNombre());
        }
        if(clienteInput.getApellido() != null){
            cliente.setApellido(clienteInput.getApellido());
        }
        if(clienteInput.getEmail() != null){
            cliente.setEmail(clienteInput.getEmail());
        }
        if(clienteInput.getEdad() != null){
            cliente.setEdad(clienteInput.getEdad());
        }
        if(clienteInput.getTelefono() != null){
            cliente.setTelefono(clienteInput.getTelefono());
        }
        if(clienteInput.getListaViajes() != null){
            List<Viaje> viajeList = getViajesFromIds(clienteInput.getListaViajes());
            cliente.getListaViajes().forEach(viaje -> {
                viaje.getListaPasajeros().remove(cliente);
                viajeRepository.save(viaje);
            });
            cliente.setListaViajes(viajeList);
            viajeList.forEach(viaje -> {
                viaje.getListaPasajeros().add(cliente);
                viajeRepository.save(viaje);
            });
        }
        return clienteRepository.save(cliente).clienteToClienteOutput();
    }

    @Override
    public List<ClienteOutput> getAllCliente(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return clienteRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Cliente::clienteToClienteOutput).toList();
    }

    @Override
    public ClienteOutput getCliente(int id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente con ID: " + id));
        return cliente.clienteToClienteOutput();
    }

    // Otros metodos

    @Override
    public int countPasajeros(int idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje).orElseThrow(() -> new EntityNotFoundException("No se encontró el viaje con Id " + idViaje));
        return viaje.getListaPasajeros().size();
    }
}
