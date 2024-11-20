package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.ServiceReservation;
import co.edu.unihumboldt.parking.mapping.dtos.ServiceReservationDto;

import java.util.List;

public class ServiceReservationMapper {
    public static ServiceReservationDto mapFrom(ServiceReservation source){
        return ServiceReservationDto.builder()
                .id(source.getId())
                .reservation(source.getReservation())
                .addService(source.getAddService())
                .build();
    }

    public static ServiceReservation mapFrom(ServiceReservationDto source){
        return new ServiceReservation(source.getId(),
                source.getReservation(),
                source.getAddService()
        );
    }

    public static List<ServiceReservation> mapFrom(List<ServiceReservationDto> source){
        return source.parallelStream()
                .map(ServiceReservationMapper::mapFrom)
                .toList();

    }
    public static List<ServiceReservationDto> mapFromDto(List<ServiceReservation> source){
        return source.parallelStream()
                .map(ServiceReservationMapper::mapFrom)
                .toList();

    }
}
