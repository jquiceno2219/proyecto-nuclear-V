package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.DayWeek;
import co.edu.unihumboldt.parking.mapping.dtos.DayWeekDto;
import co.edu.unihumboldt.parking.mapping.mappers.DayWeekMapper;
import co.edu.unihumboldt.parking.repositories.DayWeekRepository;
import co.edu.unihumboldt.parking.services.DayWeekService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayWeekServiceImpl implements DayWeekService {
    private final DayWeekRepository repository;

    public DayWeekServiceImpl(DayWeekRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DayWeekDto> list() {
        List<DayWeek> dayWeek = (List<DayWeek>) repository.findAll();
        return DayWeekMapper.mapFromDto(dayWeek);
    }

    @Override
    public DayWeekDto byId(int id) {
        DayWeek dayWeek = repository.findById(id).orElseThrow();
        return DayWeekMapper.mapFrom(dayWeek);
    }

    @Override
    public void add(DayWeekDto t) {
        DayWeek dayWeek = DayWeekMapper.mapFrom(t);
        repository.save(dayWeek);
    }

    @Override
    public void update(int id, DayWeekDto dayWeekDto) {
        DayWeek dayWeek = repository.findById(id).orElseThrow();
        DayWeek updated = DayWeekMapper.mapFrom(dayWeekDto);

        dayWeek.setDayWeekNumber(updated.getDayWeekNumber());
        dayWeek.setDayTitle(updated.getDayTitle());
        dayWeek.setStatus(updated.isStatus());
    }

    @Override
    public DayWeekDto toggleDayWeekStatus(int id) {
        DayWeek dayWeek = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Day Week not found with id: " + id));

        dayWeek.setStatus(!dayWeek.isStatus());
        DayWeek savedDayWeek = repository.save(dayWeek);

        return DayWeekMapper.mapFrom(savedDayWeek);
    }
}
