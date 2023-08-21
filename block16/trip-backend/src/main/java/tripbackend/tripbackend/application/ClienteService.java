package tripbackend.tripbackend.application;



import tripbackend.tripbackend.controller.dtos.input.ClienteInput;
import tripbackend.tripbackend.controller.dtos.output.ClienteOutput;

import java.util.List;

public interface ClienteService {
    ClienteOutput addCliente(ClienteInput clienteInput);
    void deleteCliente(int id);
    ClienteOutput updateCliente(Integer id, ClienteInput clienteInput);
    List<ClienteOutput> getAllCliente(int pageNumber, int pageSize);
    ClienteOutput getCliente(int id);
    int countPasajeros(int idViaje);
}
