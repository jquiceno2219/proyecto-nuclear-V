package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.ServiceBook;
import co.edu.unihumboldt.parking.mapping.dtos.ServiceBookDto;

import java.util.List;

public class ServiceBookMapper {
    public static ServiceBookDto mapFrom(ServiceBook source){
        return ServiceBookDto.builder()
                .id(source.getId())
                .book(source.getBook())
                .addService(source.getAddService())
                .build();
    }

    public static ServiceBook mapFrom(ServiceBookDto source){
        return new ServiceBook(source.getId(),
                source.getBook(),
                source.getAddService()
        );
    }

    public static List<ServiceBook> mapFrom(List<ServiceBookDto> source){
        return source.parallelStream()
                .map(ServiceBookMapper::mapFrom)
                .toList();

    }
    public static List<ServiceBookDto> mapFromDto(List<ServiceBook> source){
        return source.parallelStream()
                .map(ServiceBookMapper::mapFrom)
                .toList();

    }
}
