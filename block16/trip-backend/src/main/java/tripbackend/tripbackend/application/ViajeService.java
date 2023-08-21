package tripbackend.tripbackend.application;



import tripbackend.tripbackend.controller.dtos.input.ViajeInput;
import tripbackend.tripbackend.controller.dtos.output.ViajeOutput;

import java.util.List;

public interface ViajeService {
    ViajeOutput addViaje(ViajeInput viajeInput);
    void deleteViaje(int id);
    ViajeOutput updateViaje(Integer id, ViajeInput viajeInput);
    List<ViajeOutput> getAllViaje(int pageNumber, int pageSize);
    ViajeOutput getViaje(int id);
    ViajeOutput addPasajero(Integer idViaje, Integer idPasajero);
    ViajeOutput modifyEstado(Integer idViaje, String estado);
    String verifyViaje(Integer idViaje);
}
