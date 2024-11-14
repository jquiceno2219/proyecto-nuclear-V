package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.ParkingSpot;
import co.edu.unihumboldt.parking.mapping.dtos.ParkingSpotDto;

import java.util.List;

public class ParkingSpotMapper {

    public static ParkingSpotDto mapFrom(ParkingSpot source){
        return  ParkingSpotDto.builder()
                .id(source.getId())
                .spotNumber(source.getSpotNumber())
                .status(source.isStatus())
                .parkingFacility(source.getParkingFacility())
                .build();
    }

    public static ParkingSpot mapFrom(ParkingSpotDto source){
        return new ParkingSpot(source.getId(),
                source.getSpotNumber(),
                source.isStatus(),
                source.getParkingFacility());
    }

    public static List<ParkingSpot> mapFrom(List<ParkingSpotDto> source){
        return source.parallelStream()
                .map(ParkingSpotMapper::mapFrom)
                .toList();

    }
    public static List<ParkingSpotDto> mapFromDto(List<ParkingSpot> source){
        return source.parallelStream()
                .map(ParkingSpotMapper::mapFrom)
                .toList();

    }
}
