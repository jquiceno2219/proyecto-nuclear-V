package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.Builder;

@Builder
public record ScheduleDto( int id,
         int startTime,
         int endTime,
         boolean status) {
}
