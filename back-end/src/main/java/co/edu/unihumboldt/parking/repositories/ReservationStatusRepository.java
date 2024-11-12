package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Integer> {
}
