package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.Spot;
import co.edu.unihumboldt.parking.mapping.dtos.SpotDto;
import co.edu.unihumboldt.parking.mapping.mappers.SpotMapper;
import co.edu.unihumboldt.parking.repositories.SpotRepository;
import co.edu.unihumboldt.parking.services.SpotService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase SpotServiceImpl implementa la interfaz SpotService y se encarga de manejar la lógica
 * de negocio relacionada con los espacios de estacionamiento en el sistema. Utiliza SpotRepository
 * para acceder a los datos, permitiendo listar todos los espacios, buscar un espacio específico por
 * su ID, y agregar nuevos espacios al sistema. También ofrece la funcionalidad de actualización de
 * los detalles de un espacio existente, así como la capacidad de alternar el estado (activo/inactivo)
 * de un espacio de estacionamiento. La conversión entre entidades de espacio y objetos de transferencia
 * de datos (DTO) se lleva a cabo mediante SpotMapper, asegurando que los datos se manejen de manera
 * eficiente entre las distintas capas de la aplicación.
 */

@Service
public class SpotServiceImpl implements SpotService {
    private final SpotRepository repository;

    public SpotServiceImpl(SpotRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<SpotDto> list() {
        List<Spot> spot = repository.findAll();
        return SpotMapper.mapFromDto(spot);
    }

    @Override
    public SpotDto byId(int id) {
        Spot spot = repository.findById(id).orElseThrow();
        return SpotMapper.mapFrom(spot);
    }

    @Override
    public void add(SpotDto t) {
        Spot spot = SpotMapper.mapFrom(t);
        repository.save(spot);
    }

    @Override
    public void update(int id, SpotDto SpotDto) {
        Spot spot = repository.findById(id).orElseThrow();
        Spot updated = SpotMapper.mapFrom(SpotDto);

        spot.setSpotNumber(updated.getSpotNumber());
        spot.setStatus(updated.isStatus());
        spot.setCarPark(updated.getCarPark());

    }

    @Override
    public SpotDto toggleSpotStatus(int id) {
        Spot spot = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spot not found with id: " + id));

        spot.setStatus(!spot.isStatus());
        Spot savedSpot = repository.save(spot);

        return SpotMapper.mapFrom(savedSpot);
    }

}
