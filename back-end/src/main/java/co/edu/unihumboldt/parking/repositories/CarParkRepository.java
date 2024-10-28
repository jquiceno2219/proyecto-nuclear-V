package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarParkRepository extends JpaRepository<CarPark, Integer> {

}
