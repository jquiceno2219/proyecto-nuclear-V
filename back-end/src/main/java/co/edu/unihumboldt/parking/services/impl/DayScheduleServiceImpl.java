package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.DailySchedule;
import co.edu.unihumboldt.parking.mapping.dtos.DayScheduleDto;
import co.edu.unihumboldt.parking.mapping.mappers.DayScheduleMapper;
import co.edu.unihumboldt.parking.repositories.DayScheduleRepository;
import co.edu.unihumboldt.parking.services.DayScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase DayScheduleServiceImpl es una implementación del servicio DayScheduleService, responsable
 * de manejar la lógica de negocio relacionada con los horarios diarios en el sistema de estacionamiento.
 * Utiliza el DayScheduleRepository para realizar operaciones en la base de datos, como la obtención de
 * la lista de horarios diarios y la adición de nuevos horarios. La clase emplea el DayScheduleMapper
 * para convertir entre las entidades de horarios diarios y los objetos de transferencia de datos (DTO),
 * asegurando la correcta manipulación y transformación de los datos a través de las diferentes capas
 * de la aplicación.
 */

@Service
public class DayScheduleServiceImpl implements DayScheduleService {
    private final DayScheduleRepository repository;

    public DayScheduleServiceImpl(DayScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DayScheduleDto> list() {
        List<DailySchedule> dailySchedule = repository.findAll();
        return DayScheduleMapper.mapFromDto(dailySchedule);
    }


    @Override
    public void add(DayScheduleDto t) {
        DailySchedule dailySchedule = DayScheduleMapper.mapFrom(t);
        repository.save(dailySchedule);

    }


}
