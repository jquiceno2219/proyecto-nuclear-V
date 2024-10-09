package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import co.edu.unihumboldt.parking.mapping.dtos.CarParkDto;
import co.edu.unihumboldt.parking.mapping.mappers.CarParkMapper;
import co.edu.unihumboldt.parking.repositories.CarParkRepository;
import co.edu.unihumboldt.parking.services.CarParkService;
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
}
