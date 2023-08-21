package tripfrontend.tripfrontend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tripfrontend.tripfrontend.domain.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
