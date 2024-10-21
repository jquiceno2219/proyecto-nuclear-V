package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.VehicleTypeDto;

import java.util.List;

public interface VehicleTypeService {
    List<VehicleTypeDto> list();
    VehicleTypeDto byId(int id);
    void add(VehicleTypeDto t);
    void update(int id, VehicleTypeDto vehicleTypeDto);
    VehicleTypeDto toggleVehicleTypeStatus(int id);
}
