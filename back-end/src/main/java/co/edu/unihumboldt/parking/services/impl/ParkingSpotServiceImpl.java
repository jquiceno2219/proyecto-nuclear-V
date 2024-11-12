package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.ParkingSpot;
import co.edu.unihumboldt.parking.mapping.dtos.ParkingSpotDto;
import co.edu.unihumboldt.parking.mapping.mappers.ParkingSpotMapper;
import co.edu.unihumboldt.parking.repositories.ParkingSpotRepository;
import co.edu.unihumboldt.parking.services.ParkingSpotService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase ParkingSpotServiceImpl implementa la interfaz ParkingSpotService y se encarga de manejar la lógica
 * de negocio relacionada con los espacios de estacionamiento en el sistema. Utiliza ParkingSpotRepository
 * para acceder a los datos, permitiendo listar todos los espacios, buscar un espacio específico por
 * su ID, y agregar nuevos espacios al sistema. También ofrece la funcionalidad de actualización de
 * los detalles de un espacio existente, así como la capacidad de alternar el estado (activo/inactivo)
 * de un espacio de estacionamiento. La conversión entre entidades de espacio y objetos de transferencia
 * de datos (DTO) se lleva a cabo mediante ParkingSpotMapper, asegurando que los datos se manejen de manera
 * eficiente entre las distintas capas de la aplicación.
 */

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {
    private final ParkingSpotRepository repository;

    public ParkingSpotServiceImpl(ParkingSpotRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<ParkingSpotDto> list() {
        List<ParkingSpot> parkingSpot = repository.findAll();
        return ParkingSpotMapper.mapFromDto(parkingSpot);
    }

    @Override
    public ParkingSpotDto byId(int id) {
        ParkingSpot parkingSpot = repository.findById(id).orElseThrow();
        return ParkingSpotMapper.mapFrom(parkingSpot);
    }

    @Override
    public void add(ParkingSpotDto t) {
        ParkingSpot parkingSpot = ParkingSpotMapper.mapFrom(t);
        repository.save(parkingSpot);
    }

    @Override
    public void update(int id, ParkingSpotDto ParkingSpotDto) {
        ParkingSpot parkingSpot = repository.findById(id).orElseThrow();
        ParkingSpot updated = ParkingSpotMapper.mapFrom(ParkingSpotDto);

        parkingSpot.setSpotNumber(updated.getSpotNumber());
        parkingSpot.setStatus(updated.isStatus());
        parkingSpot.setParkingFacility(updated.getParkingFacility());

    }

    @Override
    public ParkingSpotDto toggleSpotStatus(int id) {
        ParkingSpot parkingSpot = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ParkingSpot not found with id: " + id));

        parkingSpot.setStatus(!parkingSpot.isStatus());
        ParkingSpot savedParkingSpot = repository.save(parkingSpot);

        return ParkingSpotMapper.mapFrom(savedParkingSpot);
    }

}
