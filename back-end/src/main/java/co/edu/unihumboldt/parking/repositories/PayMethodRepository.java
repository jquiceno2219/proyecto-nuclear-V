package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.PayMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayMethodRepository extends JpaRepository<PayMethod,Integer> {
}
