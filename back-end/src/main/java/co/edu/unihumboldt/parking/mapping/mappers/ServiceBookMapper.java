package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.ServiceReservation;
import co.edu.unihumboldt.parking.mapping.dtos.ServiceBookDto;

import java.util.List;

public class ServiceBookMapper {
    public static ServiceBookDto mapFrom(ServiceReservation source){
        return ServiceBookDto.builder()
                .id(source.getId())
                .reservation(source.getReservation())
                .addService(source.getAddService())
                .build();
    }

    public static ServiceReservation mapFrom(ServiceBookDto source){
        return new ServiceReservation(source.getId(),
                source.getReservation(),
                source.getAddService()
        );
    }

    public static List<ServiceReservation> mapFrom(List<ServiceBookDto> source){
        return source.parallelStream()
                .map(ServiceBookMapper::mapFrom)
                .toList();

    }
    public static List<ServiceBookDto> mapFromDto(List<ServiceReservation> source){
        return source.parallelStream()
                .map(ServiceBookMapper::mapFrom)
                .toList();

    }
}
