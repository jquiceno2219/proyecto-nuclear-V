package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.DailySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayScheduleRepository extends JpaRepository<DailySchedule,Integer> {
}
