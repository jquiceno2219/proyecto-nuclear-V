package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarParkRepository extends CrudRepository<CarPark, Integer> {

}
