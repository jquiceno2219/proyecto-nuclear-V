package co.edu.unihumboldt.parking.controller.restController.schedule;

import co.edu.unihumboldt.parking.mapping.dtos.DayWeekDto;
import co.edu.unihumboldt.parking.services.impl.DayWeekServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/days-week")
public class DayWeekController {
    private final DayWeekServiceImpl serviceDayWeek;
    private final Logger logger = LoggerFactory.getLogger(DayWeekController.class);

    public DayWeekController(DayWeekServiceImpl serviceDayWeek) {
        this.serviceDayWeek = serviceDayWeek;
    }

    @GetMapping("/list")
    public List<DayWeekDto> listDayWeeks() {
        return serviceDayWeek.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createDayWeek (@RequestParam("day_week_number") int dayWeekNumber,
                                              @RequestParam("day_title") String dayTitle,
                                              @RequestParam("status") boolean status){
        try {

            DayWeekDto dayWeekDto = DayWeekDto.builder()
                    .dayWeekNumber(dayWeekNumber)
                    .dayTitle(dayTitle)
                    .status(status)
                    .build();
            serviceDayWeek.add(dayWeekDto);
            return ResponseEntity.ok("Day Week Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Day Week: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDayWeek(
            @PathVariable("id") int id,
            @RequestParam("day_week_number") int dayWeekNumber,
            @RequestParam("day_title") String dayTitle,
            @RequestParam("status") boolean status) {

        try {
            DayWeekDto existingDayWeek = serviceDayWeek.byId(id);
            if (existingDayWeek == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day Week not found");
            }
            DayWeekDto updateDayWeek = DayWeekDto.builder()
                    .id(id)
                    .dayWeekNumber(dayWeekNumber)
                    .dayTitle(dayTitle)
                    .status(status)
                    .build();

            serviceDayWeek.add(updateDayWeek);
            return ResponseEntity.ok("DayWeek Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating DayWeek: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<DayWeekDto> toggleDayWeekStatus(@PathVariable int id) {
        try {
            DayWeekDto updatedDayWeek = serviceDayWeek.toggleDayWeekStatus(id);
            return ResponseEntity.ok(updatedDayWeek);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Day Week status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
