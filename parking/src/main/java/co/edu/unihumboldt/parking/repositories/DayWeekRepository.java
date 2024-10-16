package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.DayWeek;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayWeekRepository extends CrudRepository<DayWeek, Integer> {
}
