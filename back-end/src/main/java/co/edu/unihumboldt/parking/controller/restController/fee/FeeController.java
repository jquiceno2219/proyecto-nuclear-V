package co.edu.unihumboldt.parking.controller.restController.fee;

import co.edu.unihumboldt.parking.mapping.dtos.FeeDto;
import co.edu.unihumboldt.parking.services.impl.FeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code FeeController} que gestiona las operaciones CRUD para
 * las tarifas a través de la API. Utiliza {@code FeeServiceImpl} para acceder
 * a la lógica de negocio. Proporciona endpoints para listar, crear, actualizar
 * y alternar el estado de las tarifas. Los métodos manejan excepciones y
 * devuelven respuestas adecuadas en caso de éxito o error, además de registrar
 * errores para facilitar la depuración.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/fee")
public class FeeController {
    private final FeeServiceImpl serviceFee;
    private final Logger logger = LoggerFactory.getLogger(FeeController.class);


    @GetMapping("/list")
    public List<FeeDto> listFees() {
        return serviceFee.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createFee (@RequestBody FeeDto feeDto){
        try {
            serviceFee.add(feeDto);
            return ResponseEntity.ok("Fee Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Fee: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFee(
            @PathVariable("id") int id,
            @RequestBody FeeDto feeDto) {

        try {
            FeeDto existingFee = serviceFee.byId(id);
            if (existingFee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fee not found");
            }
            feeDto.setId(id);
            serviceFee.add(feeDto);
            return ResponseEntity.ok("Fee Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Fee: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<FeeDto> toggleFeeStatus(@PathVariable int id) {
        try {
            FeeDto updatedFee = serviceFee.toggleFeeStatus(id);
            return ResponseEntity.ok(updatedFee);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Fee status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
