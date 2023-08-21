package tripfrontend.tripfrontend.application;

import tripfrontend.tripfrontend.controller.dtos.output.TicketOutput;

public interface TicketService {
    TicketOutput generateTicket(int userId, int viajeId);
}
