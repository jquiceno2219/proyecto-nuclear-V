package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.ServiceReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceBookRepository extends JpaRepository<ServiceReservation, Integer> {
}
