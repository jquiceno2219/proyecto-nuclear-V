package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import co.edu.unihumboldt.parking.mapping.dtos.CarParkDto;

import java.util.List;

public class CarParkMapper {
    public static CarParkDto mapFrom(CarPark source){
        return CarParkDto.builder()
                .id(source.getId())
                .name(source.getName())
                .address(source.getAddress())
                .phoneNumber(source.getPhoneNumber())
                .nit(source.getNit())
                .coordX(source.getCoordX())
                .coordY(source.getCoordY())
                .status(source.isStatus())
                .build();
    }

    public static CarPark mapFrom(CarParkDto source){
        return new CarPark(source.getId(),
                source.getName(),
                source.getAddress(),
                source.getPhoneNumber(),
                source.getNit(),
                source.getCoordX(),
                source.getCoordY(),
                source.isStatus());
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
