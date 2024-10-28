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


import co.edu.unihumboldt.parking.domain.entities.CarPark;
import co.edu.unihumboldt.parking.mapping.dtos.CarParkDto;
import co.edu.unihumboldt.parking.mapping.mappers.CarParkMapper;
import co.edu.unihumboldt.parking.repositories.CarParkRepository;
import co.edu.unihumboldt.parking.services.CarParkService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarParkServiceImpl implements CarParkService {
    private final CarParkRepository repository;

    public CarParkServiceImpl(CarParkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CarParkDto> list() {
        List<CarPark> carPark =  repository.findAll();
        return CarParkMapper.mapFromDto(carPark);
    }

    @Override
    public CarParkDto byId(int id) {
        CarPark carPark = repository.findById(id).orElseThrow();
        return CarParkMapper.mapFrom(carPark);
    }

    @Override
    public void add(CarParkDto t) {
        CarPark carPark = CarParkMapper.mapFrom(t);
        repository.save(carPark);
    }

    @Override
    public void update(int id, CarParkDto carParkDto) {
        CarPark carPark = repository.findById(id).orElseThrow();
        CarPark updated = CarParkMapper.mapFrom(carParkDto);

        carPark.setName(updated.getName());
        carPark.setAddress(updated.getAddress());
        carPark.setPhoneNumber(updated.getPhoneNumber());
        carPark.setNit(updated.getNit());
        carPark.setCoordX(updated.getCoordX());
        carPark.setCoordY(updated.getCoordY());
        carPark.setStatus(updated.isStatus());
    }

    @Override
    public CarParkDto toggleCarParkStatus(int id) {
        CarPark carPark = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car Park not found with id: " + id));

        carPark.setStatus(!carPark.isStatus());
        CarPark savedCarpark = repository.save(carPark);

        return CarParkMapper.mapFrom(savedCarpark);
    }
}
