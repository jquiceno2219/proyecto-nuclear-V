package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.ReservationDto;

import java.util.List;

public interface ReservationService {
    List<ReservationDto> list();
    ReservationDto byId(int id);
    void add(ReservationDto t);
    void update(int id, ReservationDto reservationDto);

}
