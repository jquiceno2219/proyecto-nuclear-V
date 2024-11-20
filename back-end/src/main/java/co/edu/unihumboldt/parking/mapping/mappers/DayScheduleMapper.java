package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.DailySchedule;
import co.edu.unihumboldt.parking.mapping.dtos.DayScheduleDto;

import java.util.List;

public class DayScheduleMapper {
    public static DayScheduleDto mapFrom(DailySchedule source) {
        return  DayScheduleDto.builder()
                .id(source.getId())
                .schedule(source.getSchedule())
                .dayOfWeek(source.getDayOfWeek())
                .parkingFacility(source.getParkingFacility())
                .build();
    }

    public static DailySchedule mapFrom(DayScheduleDto source){
        return new DailySchedule(source.getId(),
                source.getSchedule(),
                source.getDayOfWeek(),
                source.getParkingFacility());
    }

    public static List<DailySchedule> mapFrom(List<DayScheduleDto> source){
        return source.parallelStream()
                .map(DayScheduleMapper::mapFrom)
                .toList();

    }
    public static List<DayScheduleDto> mapFromDto(List<DailySchedule> source){
        return source.parallelStream()
                .map(DayScheduleMapper::mapFrom)
                .toList();

    }
}
