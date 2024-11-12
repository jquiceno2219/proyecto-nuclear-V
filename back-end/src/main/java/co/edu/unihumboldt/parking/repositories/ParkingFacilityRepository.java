package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.ParkingFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingFacilityRepository extends JpaRepository<ParkingFacility, Integer> {

}
