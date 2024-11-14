package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.ReservationStatus;
import co.edu.unihumboldt.parking.mapping.dtos.ReservationStatusDto;

import java.util.List;

public class ReservationStatusMapper {
    public static ReservationStatusDto mapFrom(ReservationStatus source) {
        return ReservationStatusDto.builder()
                .id(source.getId())
                .name(source.getName())
                .status(source.isStatus())
                .build();
    }

    public static ReservationStatus mapFrom(ReservationStatusDto source){
        return new ReservationStatus(source.getId(),
                source.getName(),
                source.isStatus());
    }

    public static List<ReservationStatus> mapFrom(List<ReservationStatusDto> source){
        return source.parallelStream()
                .map(ReservationStatusMapper::mapFrom)
                .toList();

    }
    public static List<ReservationStatusDto> mapFromDto(List<ReservationStatus> source){
        return source.parallelStream()
                .map(ReservationStatusMapper::mapFrom)
                .toList();

    }
}
