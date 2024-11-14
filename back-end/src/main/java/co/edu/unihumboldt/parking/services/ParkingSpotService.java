package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.ParkingSpotDto;

import java.util.List;

public interface ParkingSpotService {
    List<ParkingSpotDto> list();
    ParkingSpotDto byId(int id);
    void add(ParkingSpotDto t);
    void update(int id, ParkingSpotDto parkingSpotDto);
    ParkingSpotDto toggleSpotStatus(int id);
}
