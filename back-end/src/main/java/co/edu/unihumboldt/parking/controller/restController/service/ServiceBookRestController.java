package co.edu.unihumboldt.parking.controller.restController.service;

import co.edu.unihumboldt.parking.mapping.dtos.ServiceBookDto;
import co.edu.unihumboldt.parking.services.impl.ServiceBookServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code ServiceBookRestController} que gestiona las operaciones CRUD para
 * la reserva de servicios a través de la API. Utiliza {@code ServiceBookServiceImpl}
 * para acceder a la lógica de negocio. Proporciona endpoints para listar, crear
 * y actualizar reservas de servicios. Los métodos manejan excepciones y devuelven
 * respuestas adecuadas en caso de éxito o error, incluyendo códigos de estado HTTP
 * para reflejar el resultado de las operaciones.
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/service-books")
public class ServiceBookRestController {

    private final ServiceBookServiceImpl serviceBookService;

    @GetMapping("/list")
    public ResponseEntity<List<ServiceBookDto>> listServiceBooks() {
        List<ServiceBookDto> serviceBooks = serviceBookService.list();
        return ResponseEntity.ok(serviceBooks);
    }

    @PostMapping("/new")
    public ResponseEntity<String> createServiceBook(@RequestBody ServiceBookDto serviceBookDto) {
        try {
            serviceBookService.add(serviceBookDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("ServiceReservation Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating ServiceReservation: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateServiceBook(@PathVariable("id") int id,
                                                    @RequestBody ServiceBookDto serviceBookDto) {
        try {
            ServiceBookDto existingServiceBook = serviceBookService.byId(id);
            if (existingServiceBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ServiceReservation not found");
            }
            serviceBookDto.setId(id);
            serviceBookService.add(serviceBookDto);  // Mejor uso de un método `update`
            return ResponseEntity.ok("ServiceReservation Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating ServiceReservation: " + e.getMessage());
        }
    }
}
