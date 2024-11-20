package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.ServiceReservationDto;

import java.util.List;

public interface ServiceReservationService {
    List<ServiceReservationDto> list();
    ServiceReservationDto byId(int id);
    void add(ServiceReservationDto t);
    void update(int id, ServiceReservationDto ServiceReservationDto);
}
