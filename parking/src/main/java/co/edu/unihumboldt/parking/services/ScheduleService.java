package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.ScheduleDto;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDto> list();
    ScheduleDto byId(int id);
    void add(ScheduleDto t);
    void update(int id, ScheduleDto scheduleDto);
    ScheduleDto toggleScheduleStatus(int id);
}
