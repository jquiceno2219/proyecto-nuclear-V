package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.ParkingFacilityDto;

import java.util.List;

public interface ParkingFacilityService {
    List<ParkingFacilityDto> list();
    ParkingFacilityDto byId(int id);
    void add(ParkingFacilityDto t);
    void update(int id, ParkingFacilityDto parkingFacilityDto);
    ParkingFacilityDto toggleCarParkStatus(int id);
}
