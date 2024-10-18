package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserNameAndStatus(String userName, boolean status);
}
