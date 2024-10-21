package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
}
