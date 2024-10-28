package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.DayWeek;
import co.edu.unihumboldt.parking.mapping.dtos.DayWeekDto;

import java.util.List;

public class DayWeekMapper {
    public static DayWeekDto mapFrom(DayWeek source){
        return DayWeekDto.builder()
                .id(source.getId())
                .dayWeekNumber(source.getDayWeekNumber())
                .dayTitle(source.getDayTitle())
                .status(source.isStatus())
                .build();

    }

    public static DayWeek mapFrom(DayWeekDto source){
        return new DayWeek(source.getId(),
                source.getDayWeekNumber(),
                source.getDayTitle(),
                source.isStatus()
        );
    }

    public static List<DayWeek> mapFrom(List<DayWeekDto> source){
        return source.parallelStream()
                .map(DayWeekMapper::mapFrom)
                .toList();

    }
    public static List<DayWeekDto> mapFromDto(List<DayWeek> source){
        return source.parallelStream()
                .map(DayWeekMapper::mapFrom)
                .toList();

    }
}
