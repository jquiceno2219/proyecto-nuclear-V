package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import co.edu.unihumboldt.parking.domain.entities.VehicleType;
import lombok.Builder;

@Builder
public record FeeDto( int id,
         String name,
         int price,
         boolean status,
         CarPark carPark,
         VehicleType vehicleType) {
}
