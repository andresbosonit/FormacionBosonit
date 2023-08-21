package tripbackend.tripbackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tripbackend.tripbackend.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
