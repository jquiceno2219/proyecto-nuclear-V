package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.AddService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddServiceRepository extends JpaRepository<AddService, Integer> {
}
