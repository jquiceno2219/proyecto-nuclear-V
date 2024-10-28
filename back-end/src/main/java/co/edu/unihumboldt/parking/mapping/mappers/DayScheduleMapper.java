package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.DaySchedule;
import co.edu.unihumboldt.parking.mapping.dtos.DayScheduleDto;

import java.util.List;

public class DayScheduleMapper {
    public static DayScheduleDto mapFrom(DaySchedule source) {
        return  DayScheduleDto.builder()
                .id(source.getId())
                .schedule(source.getSchedule())
                .dayWeek(source.getDayWeek())
                .carPark(source.getCarPark())
                .build();
    }

    public static DaySchedule mapFrom(DayScheduleDto source){
        return new DaySchedule(source.getId(),
                source.getSchedule(),
                source.getDayWeek(),
                source.getCarPark());
    }

    public static List<DaySchedule> mapFrom(List<DayScheduleDto> source){
        return source.parallelStream()
                .map(DayScheduleMapper::mapFrom)
                .toList();

    }
    public static List<DayScheduleDto> mapFromDto(List<DaySchedule> source){
        return source.parallelStream()
                .map(DayScheduleMapper::mapFrom)
                .toList();

    }
}
