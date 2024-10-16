package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {
}
