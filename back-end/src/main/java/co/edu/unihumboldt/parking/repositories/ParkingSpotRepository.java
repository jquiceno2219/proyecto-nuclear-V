package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Integer> {
}
