package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer> {
}
