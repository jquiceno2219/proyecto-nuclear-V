package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.PayMethod;
import co.edu.unihumboldt.parking.mapping.dtos.PayMethodDto;

import java.util.List;

public class PayMethodMapper {
    public static PayMethodDto mapFrom(PayMethod source){
        return PayMethodDto.builder()
                .id(source.getId())
                .name(source.getName())
                .status(source.isStatus())
                .build();

    }

    public static PayMethod mapFrom(PayMethodDto source){
        return new PayMethod(source.getId(),
                source.getName(),
                source.isStatus());
    }

    public static List<PayMethod> mapFrom(List<PayMethodDto> source){
        return source.parallelStream()
                .map(PayMethodMapper::mapFrom)
                .toList();

    }
    public static List<PayMethodDto> mapFromDto(List<PayMethod> source){
        return source.parallelStream()
                .map(PayMethodMapper::mapFrom)
                .toList();

    }
}
