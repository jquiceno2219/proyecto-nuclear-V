package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.Builder;

@Builder
public record DayWeekDto(int id,
         int dayWeekNumber,
         String dayTitle,
         boolean status) {
}
