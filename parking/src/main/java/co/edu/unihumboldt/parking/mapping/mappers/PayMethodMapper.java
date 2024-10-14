package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.PayMethod;
import co.edu.unihumboldt.parking.mapping.dtos.PayMethodDto;

import java.util.List;

public class PayMethodMapper {
    public static PayMethodDto mapFrom(PayMethod source){
        return new PayMethodDto(source.getId(),
                source.getName(),
                source.isStatus());
    }

    public static PayMethod mapFrom(PayMethodDto source){
        return new PayMethod(source.id(),
                source.name(),
                source.status());
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
