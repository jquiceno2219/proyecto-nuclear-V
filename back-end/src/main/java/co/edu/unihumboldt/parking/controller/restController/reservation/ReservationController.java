package co.edu.unihumboldt.parking.controller.restController.reservation;

import co.edu.unihumboldt.parking.mapping.dtos.ReservationDto;
import co.edu.unihumboldt.parking.services.impl.ReservationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase {@code ReservationController} que gestiona las operaciones CRUD para reservas
 * a través de la API. Utiliza {@code ReservationServiceImpl} para acceder a la lógica
 * de negocio. Proporciona endpoints para listar reservas, crear una nueva reserva
 * y actualizar una reserva existente. Los métodos manejan excepciones y devuelven
 * respuestas adecuadas en caso de éxito o error.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/reservations")
public class ReservationController {

    private final ReservationServiceImpl serviceReservation;

    @GetMapping("/list")
    public List<ReservationDto> listReservation() {
        return serviceReservation.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createReservation (@RequestBody ReservationDto reservationDto){
        try {

            serviceReservation.add(reservationDto);
            return ResponseEntity.ok("Reservation Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Reservation: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateReservation(
            @PathVariable("id") int id,
            @RequestBody ReservationDto reservationDto) {

        try {
            ReservationDto existingBook = serviceReservation.byId(id);
            if (existingBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
            }
            reservationDto.setId(id);
            serviceReservation.add(reservationDto);
            return ResponseEntity.ok("Reservation  Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Reservation : " + e.getMessage());
        }
    }
}
