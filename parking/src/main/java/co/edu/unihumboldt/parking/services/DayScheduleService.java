package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.domain.entities.DayScheduleId;
import co.edu.unihumboldt.parking.mapping.dtos.DayScheduleDto;

import java.util.List;

public interface DayScheduleService {
    List<DayScheduleDto> list();
    void add(DayScheduleDto t);

}
