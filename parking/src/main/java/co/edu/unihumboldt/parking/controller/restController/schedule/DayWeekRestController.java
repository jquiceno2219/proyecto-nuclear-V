package co.edu.unihumboldt.parking.controller.restController.schedule;

import co.edu.unihumboldt.parking.mapping.dtos.DayWeekDto;
import co.edu.unihumboldt.parking.services.impl.DayWeekServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code DayWeekRestController} que gestiona las operaciones CRUD para
 * los días de la semana a través de la API. Utiliza {@code DayWeekServiceImpl}
 * para acceder a la lógica de negocio. Proporciona endpoints para listar, crear,
 * actualizar y alternar el estado de los días de la semana. Los métodos manejan
 * excepciones y devuelven respuestas adecuadas en caso de éxito o error,
 * registrando errores para facilitar la depuración.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/days-week")
public class DayWeekRestController {
    private final DayWeekServiceImpl serviceDayWeek;
    private final Logger logger = LoggerFactory.getLogger(DayWeekRestController.class);

    @GetMapping("/list")
    public List<DayWeekDto> listDayWeeks() {
        return serviceDayWeek.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createDayWeek (@RequestBody DayWeekDto dayWeekDto){
        try {
            serviceDayWeek.add(dayWeekDto);
            return ResponseEntity.ok("Day Week Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Day Week: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDayWeek(
            @PathVariable("id") int id,
            @RequestBody DayWeekDto dayWeekDto) {

        try {
            DayWeekDto existingDayWeek = serviceDayWeek.byId(id);
            if (existingDayWeek == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day Week not found");
            }
            dayWeekDto.setId(id);
            serviceDayWeek.add(dayWeekDto);
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
