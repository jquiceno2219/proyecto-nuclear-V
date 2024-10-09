package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.CarParkDto;

import java.util.List;

public interface CarParkService {
    List<CarParkDto> list();
    CarParkDto byId(int id);
    void add(CarParkDto t);
}
