package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import co.edu.unihumboldt.parking.mapping.dtos.CarParkDto;

import java.util.List;

public class CarParkMapper {
    public static CarParkDto mapFrom(CarPark source){
        return new CarParkDto(source.getId(),
                source.getName(),
                source.getAddress(),
                source.getPhoneNumber(),
                source.getNit(),
                source.getCoordX(),
                source.getCoordY(),
                source.isStatus());
    }

    public static CarPark mapFrom(CarParkDto source){
        return new CarPark(source.id(),
                source.name(),
                source.address(),
                source.phoneNumber(),
                source.nit(),
                source.coordX(),
                source.coordY(),
                source.status());
    }

    public static List<CarPark> mapFrom(List<CarParkDto> source){
        return source.parallelStream()
                .map(CarParkMapper::mapFrom)
                .toList();

    }
    public static List<CarParkDto> mapFromDto(List<CarPark> source){
        return source.parallelStream()
                .map(CarParkMapper::mapFrom)
                .toList();

    }
}
