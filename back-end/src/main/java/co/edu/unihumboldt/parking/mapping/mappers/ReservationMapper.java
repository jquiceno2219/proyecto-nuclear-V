package co.edu.unihumboldt.parking.mapping.mappers;


import co.edu.unihumboldt.parking.domain.entities.Reservation;
import co.edu.unihumboldt.parking.mapping.dtos.ReservationDto;

import java.util.List;

public class ReservationMapper {
    public static ReservationDto mapFrom(Reservation source){
        return ReservationDto.builder().id(source.getId())
                .bookDate(source.getBookDate())
                .payDate(source.getPayDate())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .payMethod(source.getPayMethod())
                .parkingSpot(source.getParkingSpot())
                .fee(source.getFee())
                .build();
    }

    public static Reservation mapFrom(ReservationDto source){
        return new Reservation(source.getId(),
                source.getBookDate(),
                source.getPayDate(),
                source.getStartDate(),
                source.getEndDate(),
                source.getReservationStatus(),
                source.getPayMethod(),
                source.getParkingSpot(),
                source.getFee());
    }

    public static List<Reservation> mapFrom(List<ReservationDto> source){
        return source.parallelStream()
                .map(ReservationMapper::mapFrom)
                .toList();

    }
    public static List<ReservationDto> mapFromDto(List<Reservation> source){
        return source.parallelStream()
                .map(ReservationMapper::mapFrom)
                .toList();

    }
}
