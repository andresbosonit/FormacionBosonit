package tripbackend.tripbackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tripbackend.tripbackend.domain.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {
}
