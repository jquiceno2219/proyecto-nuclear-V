package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.Builder;

@Builder
public record PayMethodDto( int id,
                            String name,
                            boolean status) {
}
