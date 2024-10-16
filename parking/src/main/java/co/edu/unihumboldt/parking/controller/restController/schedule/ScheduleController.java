package co.edu.unihumboldt.parking.controller.restController.schedule;

import co.edu.unihumboldt.parking.mapping.dtos.ScheduleDto;
import co.edu.unihumboldt.parking.services.impl.ScheduleServiceImpl;
import co.edu.unihumboldt.parking.services.impl.ScheduleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/schedules")
public class ScheduleController {
    private final ScheduleServiceImpl serviceSchedule;
    
    public ScheduleController(ScheduleServiceImpl serviceSchedule) {
        this.serviceSchedule = serviceSchedule;
    }

    private final Logger logger = LoggerFactory.getLogger(ScheduleController.class);


    @GetMapping("/list")
    public List<ScheduleDto> listSchedules() {
        return serviceSchedule.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createSchedule (@RequestParam("start_time") int startTime,
                                                 @RequestParam("end_time") int endTime,
                                                 @RequestParam("status") boolean status){
        try {

            ScheduleDto scheduleDto = ScheduleDto.builder()
                    .startTime(startTime)
                    .endTime(endTime)
                    .status(status)
                    .build();
            serviceSchedule.add(scheduleDto);
            return ResponseEntity.ok("Schedule Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Schedule: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSchedule(
            @PathVariable("id") int id,
            @RequestParam("start_time") int startTime,
            @RequestParam("end_time") int endTime,
            @RequestParam("status") boolean status) {

        try {
            ScheduleDto existingSchedule = serviceSchedule.byId(id);
            if (existingSchedule == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Schedule not found");
            }
            ScheduleDto updateSchedule = ScheduleDto.builder()
                    .id(id)
                    .startTime(startTime)
                    .endTime(endTime)
                    .status(status)
                    .build();

            serviceSchedule.add(updateSchedule);
            return ResponseEntity.ok("Schedule Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Schedule: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ScheduleDto> toggleScheduleStatus(@PathVariable int id) {
        try {
            ScheduleDto updatedSchedule = serviceSchedule.toggleScheduleStatus(id);
            return ResponseEntity.ok(updatedSchedule);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Schedule status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
