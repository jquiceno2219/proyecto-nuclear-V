package co.edu.unihumboldt.parking.controller.restController.reservation;

import co.edu.unihumboldt.parking.mapping.dtos.ReservationStatusDto;
import co.edu.unihumboldt.parking.services.impl.ReservationStatusServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code ReservationStatusController} que gestiona las operaciones CRUD para
 * el estado de las reservas a través de la API. Utiliza {@code ReservationStatusServiceImpl}
 * para acceder a la lógica de negocio. Proporciona endpoints para listar, crear
 * y actualizar el estado de las reservas, así como para alternar su estado.
 * Los métodos manejan excepciones y devuelven respuestas adecuadas en caso de
 * éxito o error, incluyendo registros de errores para la depuración.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/reservations-status")
public class ReservationStatusController {
    private final ReservationStatusServiceImpl serviceBookStatus;
    private final Logger logger = LoggerFactory.getLogger(ReservationStatusController.class);


    @GetMapping("/list")
    public List<ReservationStatusDto> listBookStatus() {
        return serviceBookStatus.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createBookStatus (@RequestBody ReservationStatusDto reservationStatusDto){
        try {

            serviceBookStatus.add(reservationStatusDto);
            return ResponseEntity.ok("Reservation Status Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Reservation Status: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBookStatus(
            @PathVariable("id") int id,
            @RequestBody ReservationStatusDto reservationStatusDto) {

        try {
            ReservationStatusDto existingBookStatus = serviceBookStatus.byId(id);
            if (existingBookStatus == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation Status not found");
            }
            reservationStatusDto.setId(id);
            serviceBookStatus.add(reservationStatusDto);
            return ResponseEntity.ok("Reservation Status Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Reservation Status: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ReservationStatusDto> toggleBookStatus(@PathVariable int id) {
        try {
            ReservationStatusDto updatedBookStatus = serviceBookStatus.toggleBookStatus(id);
            return ResponseEntity.ok(updatedBookStatus);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Reservation status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
