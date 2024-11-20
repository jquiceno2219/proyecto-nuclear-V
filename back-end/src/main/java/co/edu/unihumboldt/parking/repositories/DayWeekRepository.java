package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayWeekRepository extends JpaRepository<DayOfWeek, Integer> {
}
