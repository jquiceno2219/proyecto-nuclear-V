package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.VehicleType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends CrudRepository<VehicleType, Integer> {
}
