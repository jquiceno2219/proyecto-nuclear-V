package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.DaySchedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayScheduleRepository extends CrudRepository<DaySchedule,Integer> {
}
