package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.DayOfWeek;
import co.edu.unihumboldt.parking.mapping.dtos.DayWeekDto;
import co.edu.unihumboldt.parking.mapping.mappers.DayWeekMapper;
import co.edu.unihumboldt.parking.repositories.DayWeekRepository;
import co.edu.unihumboldt.parking.services.DayWeekService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase DayWeekServiceImpl es una implementación del servicio DayWeekService, encargada de gestionar
 * la lógica de negocio relacionada con los días de la semana en el sistema de estacionamiento. Utiliza
 * el DayWeekRepository para interactuar con la base de datos, permitiendo operaciones como la obtención
 * de una lista de días de la semana y la búsqueda por ID. Además, permite añadir, actualizar y alternar
 * el estado de los días de la semana. La clase utiliza el DayWeekMapper para convertir entre las entidades
 * de días de la semana y los objetos de transferencia de datos (DTO), asegurando la correcta manipulación
 * de datos a lo largo de las distintas capas de la aplicación.
 */

@Service
public class DayWeekServiceImpl implements DayWeekService {
    private final DayWeekRepository repository;

    public DayWeekServiceImpl(DayWeekRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DayWeekDto> list() {
        List<DayOfWeek> dayOfWeek = repository.findAll();
        return DayWeekMapper.mapFromDto(dayOfWeek);
    }

    @Override
    public DayWeekDto byId(int id) {
        DayOfWeek dayOfWeek = repository.findById(id).orElseThrow();
        return DayWeekMapper.mapFrom(dayOfWeek);
    }

    @Override
    public void add(DayWeekDto t) {
        DayOfWeek dayOfWeek = DayWeekMapper.mapFrom(t);
        repository.save(dayOfWeek);
    }

    @Override
    public void update(int id, DayWeekDto dayWeekDto) {
        DayOfWeek dayOfWeek = repository.findById(id).orElseThrow();
        DayOfWeek updated = DayWeekMapper.mapFrom(dayWeekDto);

        dayOfWeek.setDayWeekNumber(updated.getDayWeekNumber());
        dayOfWeek.setDayTitle(updated.getDayTitle());
        dayOfWeek.setStatus(updated.isStatus());
    }

    @Override
    public DayWeekDto toggleDayWeekStatus(int id) {
        DayOfWeek dayOfWeek = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Day Week not found with id: " + id));

        dayOfWeek.setStatus(!dayOfWeek.isStatus());
        DayOfWeek savedDayOfWeek = repository.save(dayOfWeek);

        return DayWeekMapper.mapFrom(savedDayOfWeek);
    }
}
