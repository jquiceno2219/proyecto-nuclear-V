package co.edu.unihumboldt.parking.services.impl;
/**
 * La clase CarParkServiceImpl es una implementación del servicio CarParkService, encargada de gestionar
 * la lógica de negocio relacionada con los parqueaderos en el sistema de estacionamiento. Utiliza el
 * CarParkRepository para realizar operaciones en la base de datos, incluyendo la obtención de la lista
 * de parqueaderos, la búsqueda de un parqueadero específico por su ID, la adición de un nuevo parqueadero
 * y la actualización de la información de un parqueadero existente. Además, proporciona una funcionalidad
 * para alternar el estado (activo/inactivo) de un parqueadero. La clase utiliza el CarParkMapper para
 * convertir entre las entidades de parqueadero y los objetos de transferencia de datos (DTO), garantizando
 * la correcta manipulación de los datos a través de las diferentes capas de la aplicación.
 */


import co.edu.unihumboldt.parking.domain.entities.ParkingFacility;
import co.edu.unihumboldt.parking.mapping.dtos.ParkingFacilityDto;
import co.edu.unihumboldt.parking.mapping.mappers.ParkingFacilityMapper;
import co.edu.unihumboldt.parking.repositories.ParkingFacilityRepository;
import co.edu.unihumboldt.parking.services.ParkingFacilityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ParkingFacilityServiceImpl implements ParkingFacilityService {
    private final ParkingFacilityRepository repository;

    public ParkingFacilityServiceImpl(ParkingFacilityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ParkingFacilityDto> list() {
        List<ParkingFacility> parkingFacility =  repository.findAll();
        return ParkingFacilityMapper.mapFromDto(parkingFacility);
    }

    @Override
    public ParkingFacilityDto byId(int id) {
        ParkingFacility parkingFacility = repository.findById(id).orElseThrow();
        return ParkingFacilityMapper.mapFrom(parkingFacility);
    }

    @Override
    public void add(ParkingFacilityDto t) {
        ParkingFacility parkingFacility = ParkingFacilityMapper.mapFrom(t);
        repository.save(parkingFacility);
    }

    @Override
    public void update(int id, ParkingFacilityDto parkingFacilityDto) {
        ParkingFacility parkingFacility = repository.findById(id).orElseThrow();
        ParkingFacility updated = ParkingFacilityMapper.mapFrom(parkingFacilityDto);

        parkingFacility.setName(updated.getName());
        parkingFacility.setAddress(updated.getAddress());
        parkingFacility.setPhoneNumber(updated.getPhoneNumber());
        parkingFacility.setNit(updated.getNit());
        parkingFacility.setCoordX(updated.getCoordX());
        parkingFacility.setCoordY(updated.getCoordY());
        parkingFacility.setStatus(updated.isStatus());
    }

    @Override
    public ParkingFacilityDto toggleCarParkStatus(int id) {
        ParkingFacility parkingFacility = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car Park not found with id: " + id));

        parkingFacility.setStatus(!parkingFacility.isStatus());
        ParkingFacility savedCarpark = repository.save(parkingFacility);

        return ParkingFacilityMapper.mapFrom(savedCarpark);
    }
}
