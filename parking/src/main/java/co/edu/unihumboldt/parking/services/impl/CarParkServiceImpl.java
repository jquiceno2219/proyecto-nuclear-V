package co.edu.unihumboldt.parking.services.impl;

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
        List<CarPark> carPark = (List<CarPark>) repository.findAll();
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
