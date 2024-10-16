package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import co.edu.unihumboldt.parking.domain.entities.DayScheduleId;
import co.edu.unihumboldt.parking.domain.entities.DayWeek;
import co.edu.unihumboldt.parking.domain.entities.Schedule;
import lombok.Builder;

@Builder
public record DayScheduleDto(
        DayScheduleId id,
                              Schedule schedule,
                              DayWeek dayWeek,
                              CarPark carPark) {
}
