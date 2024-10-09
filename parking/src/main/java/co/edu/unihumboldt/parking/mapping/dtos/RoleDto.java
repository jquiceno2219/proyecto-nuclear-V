package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.Builder;

@Builder
public record RoleDto( int id,
         String name,
         boolean status) {
}
