package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.ParkingFacility;
import co.edu.unihumboldt.parking.mapping.dtos.ParkingFacilityDto;

import java.util.List;

public class ParkingFacilityMapper {
    public static ParkingFacilityDto mapFrom(ParkingFacility source){
        return ParkingFacilityDto.builder()
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

    public static ParkingFacility mapFrom(ParkingFacilityDto source){
        return new ParkingFacility(source.getId(),
                source.getName(),
                source.getAddress(),
                source.getPhoneNumber(),
                source.getNit(),
                source.getCoordX(),
                source.getCoordY(),
                source.isStatus());
    }

    public static List<ParkingFacility> mapFrom(List<ParkingFacilityDto> source){
        return source.parallelStream()
                .map(ParkingFacilityMapper::mapFrom)
                .toList();

    }
    public static List<ParkingFacilityDto> mapFromDto(List<ParkingFacility> source){
        return source.parallelStream()
                .map(ParkingFacilityMapper::mapFrom)
                .toList();

    }
}
