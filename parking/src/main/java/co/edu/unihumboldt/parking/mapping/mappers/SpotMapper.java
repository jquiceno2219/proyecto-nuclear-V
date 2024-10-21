package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.Spot;
import co.edu.unihumboldt.parking.mapping.dtos.SpotDto;

import java.util.List;

public class SpotMapper {

    public static SpotDto mapFrom(Spot source){
        return  SpotDto.builder()
                .id(source.getId())
                .spotNumber(source.getSpotNumber())
                .status(source.isStatus())
                .carPark(source.getCarPark())
                .build();
    }

    public static Spot mapFrom(SpotDto source){
        return new Spot(source.getId(),
                source.getSpotNumber(),
                source.isStatus(),
                source.getCarPark());
    }

    public static List<Spot> mapFrom(List<SpotDto> source){
        return source.parallelStream()
                .map(SpotMapper::mapFrom)
                .toList();

    }
    public static List<SpotDto> mapFromDto(List<Spot> source){
        return source.parallelStream()
                .map(SpotMapper::mapFrom)
                .toList();

    }
}
