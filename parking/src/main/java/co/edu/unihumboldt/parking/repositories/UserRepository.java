package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
