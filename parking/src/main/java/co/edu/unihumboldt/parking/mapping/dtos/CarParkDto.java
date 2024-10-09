package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.Builder;

@Builder
public record CarParkDto( int id,
         String name,
         String address,
         String phoneNumber,
         String nit,
         String coordX,
         String coordY,
         boolean status) {
}
