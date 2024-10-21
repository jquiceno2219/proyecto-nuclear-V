package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.DayWeekDto;

import java.util.List;

public interface DayWeekService {
    List<DayWeekDto> list();
    DayWeekDto byId(int id);
    void add(DayWeekDto t);
    void update(int id, DayWeekDto dayWeekDto);
    DayWeekDto toggleDayWeekStatus(int id);
}
