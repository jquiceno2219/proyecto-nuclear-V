package co.edu.unihumboldt.parking.controller.restController.payMethod;

import co.edu.unihumboldt.parking.mapping.dtos.PayMethodDto;
import co.edu.unihumboldt.parking.services.PayMethodService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code PayMethodController} que gestiona las operaciones CRUD para
 * los métodos de pago a través de la API. Utiliza {@code PayMethodService}
 * para acceder a la lógica de negocio. Proporciona endpoints para listar, crear,
 * actualizar y alternar el estado de los métodos de pago. Los métodos manejan
 * excepciones y devuelven respuestas adecuadas en caso de éxito o error,
 * incluyendo registros de errores para facilitar la depuración.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/pay-methods")
public class PayMethodController {
    private final PayMethodService servicePayMethod;
    private final Logger logger = LoggerFactory.getLogger(PayMethodController.class);

    @GetMapping("/list")
    public List<PayMethodDto> listRole() {
        return servicePayMethod.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createPayMethod (@RequestBody PayMethodDto payMethodDto){
        try {
            servicePayMethod.add(payMethodDto);
            return ResponseEntity.ok("Pay Method Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Pay Method: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePayMethod(
            @PathVariable("id") int id,
            @RequestBody PayMethodDto payMethodDto) {

        try {
            PayMethodDto existingPayMethod = servicePayMethod.byId(id);
            if (existingPayMethod == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pay Method not found");
            }
            payMethodDto.setId(id);
            servicePayMethod.add(payMethodDto);
            return ResponseEntity.ok("Pay Method Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Pay Method: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<PayMethodDto> togglePayMethodStatus(@PathVariable int id) {
        try {
            PayMethodDto updatedPayMethod = servicePayMethod.togglePayMethodStatus(id);
            return ResponseEntity.ok(updatedPayMethod);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Pay Method status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
