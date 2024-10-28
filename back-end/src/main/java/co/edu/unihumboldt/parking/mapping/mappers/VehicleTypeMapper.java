package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.VehicleType;
import co.edu.unihumboldt.parking.mapping.dtos.VehicleTypeDto;

import java.util.List;

public class VehicleTypeMapper {
    public static VehicleTypeDto mapFrom(VehicleType source){
        return VehicleTypeDto.builder()
                .id(source.getId())
                .name(source.getName())
                .status(source.isStatus())
                .build();
    }

    public static VehicleType mapFrom(VehicleTypeDto source){
        return new VehicleType(source.getId(),
                source.getName(),
                source.isStatus());
    }

    public static List<VehicleType> mapFrom(List<VehicleTypeDto> source){
        return source.parallelStream()
                .map(VehicleTypeMapper::mapFrom)
                .toList();

    }
    public static List<VehicleTypeDto> mapFromDto(List<VehicleType> source){
        return source.parallelStream()
                .map(VehicleTypeMapper::mapFrom)
                .toList();

    }
}
