package co.edu.unihumboldt.parking.controller.restController.schedule;

import co.edu.unihumboldt.parking.domain.entities.DayScheduleId;
import co.edu.unihumboldt.parking.mapping.dtos.DayScheduleDto;
import co.edu.unihumboldt.parking.mapping.mappers.CarParkMapper;
import co.edu.unihumboldt.parking.mapping.mappers.DayWeekMapper;
import co.edu.unihumboldt.parking.mapping.mappers.ScheduleMapper;
import co.edu.unihumboldt.parking.services.impl.CarParkServiceImpl;
import co.edu.unihumboldt.parking.services.impl.DayScheduleServiceImpl;
import co.edu.unihumboldt.parking.services.impl.DayWeekServiceImpl;
import co.edu.unihumboldt.parking.services.impl.ScheduleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/days-schedule")
public class DayScheduleController {
    private final DayScheduleServiceImpl serviceDaySchedule;
    private final ScheduleServiceImpl serviceSchedule;
    private final DayWeekServiceImpl serviceWeekSchedule;
    private final CarParkServiceImpl serviceCarPark;


    public DayScheduleController(DayScheduleServiceImpl serviceDaySchedule, ScheduleServiceImpl serviceSchedule, DayWeekServiceImpl serviceWeekSchedule, CarParkServiceImpl serviceCarPark) {
        this.serviceDaySchedule = serviceDaySchedule;
        this.serviceSchedule = serviceSchedule;
        this.serviceWeekSchedule = serviceWeekSchedule;
        this.serviceCarPark = serviceCarPark;
    }


    @GetMapping("/list")
    public List<DayScheduleDto> listDaySchedules() {
        return serviceDaySchedule.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createDaySchedule (@RequestParam("schedule") int scheduleId,
                                              @RequestParam("password") int dayWeekId,
                                              @RequestParam("car_park")int carParkId){
        try {

            DayScheduleDto dayScheduleDto = DayScheduleDto.builder()
                    .schedule(ScheduleMapper.mapFrom(serviceSchedule.byId(scheduleId)))
                    .dayWeek(DayWeekMapper.mapFrom(serviceWeekSchedule.byId(dayWeekId)))
                    .carPark(CarParkMapper.mapFrom(serviceCarPark.byId(carParkId)))
                    .build();
            serviceDaySchedule.add(dayScheduleDto);
            return ResponseEntity.ok("Day Schedule Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Day Schedule: " + e.getMessage());
        }
    }


}
