package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.Schedule;
import co.edu.unihumboldt.parking.mapping.dtos.ScheduleDto;

import java.util.List;

public class ScheduleMapper {
    public static ScheduleDto mapFrom(Schedule source){
        return new ScheduleDto(source.getId(),
                source.getStartTime(),
                source.getEndTime(),
                source.isStatus());
    }

    public static Schedule mapFrom(ScheduleDto source){
        return new Schedule(source.id(),
                source.startTime(),
                source.endTime(),
                source.status());
    }

    public static List<Schedule> mapFrom(List<ScheduleDto> source){
        return source.parallelStream()
                .map(ScheduleMapper::mapFrom)
                .toList();

    }
    public static List<ScheduleDto> mapFromDto(List<Schedule> source){
        return source.parallelStream()
                .map(ScheduleMapper::mapFrom)
                .toList();

    }
}
