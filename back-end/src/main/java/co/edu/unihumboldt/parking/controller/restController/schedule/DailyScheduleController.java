package co.edu.unihumboldt.parking.controller.restController.schedule;

import co.edu.unihumboldt.parking.mapping.dtos.DayScheduleDto;
import co.edu.unihumboldt.parking.services.impl.DayScheduleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code DailyScheduleController} que gestiona las operaciones CRUD
 * para los horarios diarios a través de la API. Utiliza {@code DayScheduleServiceImpl}
 * para acceder a la lógica de negocio. Proporciona endpoints para listar y crear
 * horarios diarios. Los métodos manejan excepciones y devuelven respuestas adecuadas
 * en caso de éxito o error.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/days-schedules")
public class DailyScheduleController {
    private final DayScheduleServiceImpl serviceDaySchedule;

    @GetMapping("/list")
    public List<DayScheduleDto> listDaySchedules() {
        return serviceDaySchedule.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createDaySchedule (@RequestBody DayScheduleDto dayScheduleDto){
        try {
            serviceDaySchedule.add(dayScheduleDto);
            return ResponseEntity.ok("Day Schedule Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Day Schedule: " + e.getMessage());
        }
    }


}
