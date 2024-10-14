package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.Fee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepository extends CrudRepository<Fee, Integer> {

}
