package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.AddService;
import co.edu.unihumboldt.parking.mapping.dtos.AddServiceDto;

import java.util.List;

public class AddServiceMapper {
    public static AddServiceDto mapFrom(AddService source){
        return  AddServiceDto.builder()
                .id(source.getId())
                .name(source.getName())
                .price(source.getPrice())
                .status(source.isStatus())
                .carPark(source.getCarPark())
                .build();
    }

    public static AddService mapFrom(AddServiceDto source){
        return new AddService(source.getId(),
                source.getName(),
                source.getPrice(),
                source.isStatus(),
                source.getCarPark()
     );
    }

    public static List<AddService> mapFrom(List<AddServiceDto> source){
        return source.parallelStream()
                .map(AddServiceMapper::mapFrom)
                .toList();

    }
    public static List<AddServiceDto> mapFromDto(List<AddService> source){
        return source.parallelStream()
                .map(AddServiceMapper::mapFrom)
                .toList();

    }
}
