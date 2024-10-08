package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import co.edu.unihumboldt.parking.domain.entities.Role;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Builder
public record UserDto( int id,
         String userName,
         String password,
         String name,
         String idNumber,
         String phoneNumber,
         String email,
         boolean status,
         CarPark carPark,
         Role role) {
}
