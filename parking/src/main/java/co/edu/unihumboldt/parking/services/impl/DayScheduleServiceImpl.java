package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.DaySchedule;
import co.edu.unihumboldt.parking.domain.entities.DayScheduleId;
import co.edu.unihumboldt.parking.mapping.dtos.DayScheduleDto;
import co.edu.unihumboldt.parking.mapping.mappers.DayScheduleMapper;
import co.edu.unihumboldt.parking.repositories.DayScheduleRepository;
import co.edu.unihumboldt.parking.services.DayScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayScheduleServiceImpl implements DayScheduleService {
    private final DayScheduleRepository repository;

    public DayScheduleServiceImpl(DayScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DayScheduleDto> list() {
        List<DaySchedule> daySchedule = (List<DaySchedule>) repository.findAll();
        return DayScheduleMapper.mapFromDto(daySchedule);
    }


    @Override
    public void add(DayScheduleDto t) {
        DaySchedule daySchedule = DayScheduleMapper.mapFrom(t);
        repository.save(daySchedule);

    }


}
