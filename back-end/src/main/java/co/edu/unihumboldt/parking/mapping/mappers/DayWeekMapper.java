package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.DayOfWeek;
import co.edu.unihumboldt.parking.mapping.dtos.DayWeekDto;

import java.util.List;

public class DayWeekMapper {
    public static DayWeekDto mapFrom(DayOfWeek source){
        return DayWeekDto.builder()
                .id(source.getId())
                .dayWeekNumber(source.getDayWeekNumber())
                .dayTitle(source.getDayTitle())
                .status(source.isStatus())
                .build();

    }

    public static DayOfWeek mapFrom(DayWeekDto source){
        return new DayOfWeek(source.getId(),
                source.getDayWeekNumber(),
                source.getDayTitle(),
                source.isStatus()
        );
    }

    public static List<DayOfWeek> mapFrom(List<DayWeekDto> source){
        return source.parallelStream()
                .map(DayWeekMapper::mapFrom)
                .toList();

    }
    public static List<DayWeekDto> mapFromDto(List<DayOfWeek> source){
        return source.parallelStream()
                .map(DayWeekMapper::mapFrom)
                .toList();

    }
}
