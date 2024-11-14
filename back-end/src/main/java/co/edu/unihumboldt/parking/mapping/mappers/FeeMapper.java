package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.Fee;
import co.edu.unihumboldt.parking.mapping.dtos.FeeDto;

import java.util.List;

public class FeeMapper {
    public static FeeDto mapFrom(Fee source){
        return FeeDto.builder()
                .id(source.getId())
                .name(source.getName())
                .price(source.getPrice())
                .status(source.isStatus())
                .parkingFacility(source.getParkingFacility())
                .vehicleType(source.getVehicleType())
                .build();
    }

    public static Fee mapFrom(FeeDto source){
        return new Fee(source.getId(),
                source.getName(),
                source.getPrice(),
                source.isStatus(),
                source.getParkingFacility(),
                source.getVehicleType());
    }

    public static List<Fee> mapFrom(List<FeeDto> source){
        return source.parallelStream()
                .map(FeeMapper::mapFrom)
                .toList();

    }
    public static List<FeeDto> mapFromDto(List<Fee> source){
        return source.parallelStream()
                .map(FeeMapper::mapFrom)
                .toList();

    }
}
