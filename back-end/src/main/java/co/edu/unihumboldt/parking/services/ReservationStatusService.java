package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.ReservationStatusDto;

import java.util.List;

public interface ReservationStatusService {
    List<ReservationStatusDto> list();
    ReservationStatusDto byId(int id);
    void add(ReservationStatusDto t);
    void update(int id, ReservationStatusDto bookStatus);
    ReservationStatusDto toggleBookStatus(int id);
}
