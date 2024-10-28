package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.Schedule;
import co.edu.unihumboldt.parking.mapping.dtos.ScheduleDto;
import co.edu.unihumboldt.parking.mapping.mappers.ScheduleMapper;
import co.edu.unihumboldt.parking.repositories.ScheduleRepository;
import co.edu.unihumboldt.parking.services.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase ScheduleServiceImpl implementa la interfaz ScheduleService y se encarga de manejar
 * la lógica de negocio asociada a la gestión de horarios en el sistema de estacionamiento. Utiliza
 * ScheduleRepository para realizar operaciones de acceso a datos, incluyendo la obtención de todos
 * los horarios, la búsqueda de un horario específico por su ID, la adición de nuevos horarios y la
 * actualización de horarios existentes. Además, permite alternar el estado de un horario, facilitando
 * su activación o desactivación. La clase utiliza ScheduleMapper para convertir entre entidades de
 * horario y objetos de transferencia de datos (DTO), asegurando una manipulación y transmisión
 * eficiente de la información a través de las distintas capas de la aplicación.
 */

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;

    public ScheduleServiceImpl(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ScheduleDto> list() {
        List<Schedule> schedule =  repository.findAll();
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
