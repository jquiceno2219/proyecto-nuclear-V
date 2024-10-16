package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.Schedule;
import co.edu.unihumboldt.parking.mapping.dtos.ScheduleDto;
import co.edu.unihumboldt.parking.mapping.mappers.ScheduleMapper;
import co.edu.unihumboldt.parking.repositories.ScheduleRepository;
import co.edu.unihumboldt.parking.services.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;

    public ScheduleServiceImpl(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ScheduleDto> list() {
        List<Schedule> schedule = (List<Schedule>) repository.findAll();
        return ScheduleMapper.mapFromDto(schedule);
    }

    @Override
    public ScheduleDto byId(int id) {
        Schedule schedule = repository.findById(id).orElseThrow();
        return ScheduleMapper.mapFrom(schedule);
    }

    @Override
    public void add(ScheduleDto t) {
        Schedule schedule = ScheduleMapper.mapFrom(t);
        repository.save(schedule);
    }

    @Override
    public void update(int id, ScheduleDto scheduleDto) {
        Schedule schedule = repository.findById(id).orElseThrow();
        Schedule updated = ScheduleMapper.mapFrom(scheduleDto);

        schedule.setStartTime(updated.getStartTime());
        schedule.setEndTime(updated.getEndTime());
        schedule.setStatus(updated.isStatus());
    }

    @Override
    public ScheduleDto toggleScheduleStatus(int id) {
        Schedule schedule = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + id));

        schedule.setStatus(!schedule.isStatus());
        Schedule savedSchedule = repository.save(schedule);

        return ScheduleMapper.mapFrom(savedSchedule);
    }
}
