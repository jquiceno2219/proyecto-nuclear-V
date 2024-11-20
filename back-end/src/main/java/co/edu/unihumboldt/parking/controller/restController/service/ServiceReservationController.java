package co.edu.unihumboldt.parking.controller.restController.service;

import co.edu.unihumboldt.parking.mapping.dtos.ServiceReservationDto;
import co.edu.unihumboldt.parking.services.impl.ServiceReservationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code ServiceReservationController} que gestiona las operaciones CRUD para
 * la reserva de servicios a través de la API. Utiliza {@code ServiceReservationServiceImpl}
 * para acceder a la lógica de negocio. Proporciona endpoints para listar, crear
 * y actualizar reservas de servicios. Los métodos manejan excepciones y devuelven
 * respuestas adecuadas en caso de éxito o error, incluyendo códigos de estado HTTP
 * para reflejar el resultado de las operaciones.
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/service-reservations")
public class ServiceReservationController {

    private final ServiceReservationServiceImpl serviceBookService;

    @GetMapping("/list")
    public ResponseEntity<List<ServiceReservationDto>> listServiceBooks() {
        List<ServiceReservationDto> serviceBooks = serviceBookService.list();
        return ResponseEntity.ok(serviceBooks);
    }

    @PostMapping("/new")
    public ResponseEntity<String> createServiceBook(@RequestBody ServiceReservationDto serviceReservationDto) {
        try {
            serviceBookService.add(serviceReservationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("ServiceReservation Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating ServiceReservation: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateServiceBook(@PathVariable("id") int id,
                                                    @RequestBody ServiceReservationDto serviceReservationDto) {
        try {
            ServiceReservationDto existingServiceBook = serviceBookService.byId(id);
            if (existingServiceBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ServiceReservation not found");
            }
            serviceReservationDto.setId(id);
            serviceBookService.add(serviceReservationDto);  // Mejor uso de un método `update`
            return ResponseEntity.ok("ServiceReservation Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating ServiceReservation: " + e.getMessage());
        }
    }
}
