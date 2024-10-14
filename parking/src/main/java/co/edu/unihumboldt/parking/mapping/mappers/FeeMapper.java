package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.Fee;
import co.edu.unihumboldt.parking.mapping.dtos.FeeDto;

import java.util.List;

public class FeeMapper {
    public static FeeDto mapFrom(Fee source){
        return new FeeDto(source.getId(),
                source.getName(),
                source.getPrice(),
                source.isStatus(),
                source.getCarPark(),
                source.getVehicleType());
    }

    public static Fee mapFrom(FeeDto source){
        return new Fee(source.id(),
                source.name(),
                source.price(),
                source.status(),
                source.carPark(),
                source.vehicleType());
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
