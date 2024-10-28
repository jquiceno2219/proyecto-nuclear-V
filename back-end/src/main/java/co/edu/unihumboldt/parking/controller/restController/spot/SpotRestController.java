package co.edu.unihumboldt.parking.controller.restController.spot;

import co.edu.unihumboldt.parking.mapping.dtos.SpotDto;
import co.edu.unihumboldt.parking.services.impl.SpotServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code SpotRestController} que gestiona las operaciones CRUD para los
 * spots a través de la API. Utiliza {@code SpotServiceImpl} para interactuar
 * con la lógica de negocio. Proporciona endpoints para listar, crear, actualizar
 * y alternar el estado de los spots. Los métodos manejan excepciones y devuelven
 * respuestas adecuadas, incluyendo códigos de estado HTTP para reflejar el resultado
 * de las operaciones.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/spots")
public class SpotRestController {
    private final SpotServiceImpl serviceSpot;

    private final Logger logger = LoggerFactory.getLogger(SpotRestController.class);

    @GetMapping("/list")
    public List<SpotDto> listSpots() {
        return serviceSpot.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createSpot (@RequestBody SpotDto spotDto){
        try {
            serviceSpot.add(spotDto);
            return ResponseEntity.ok("Spot Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Spot: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSpot(
            @PathVariable("id") int id,
            @RequestBody SpotDto spotDto) {

        try {
            SpotDto existingSpot = serviceSpot.byId(id);
            if (existingSpot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Spot not found");
            }
            spotDto.setId(id);
            serviceSpot.add(spotDto);
            return ResponseEntity.ok("Spot Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Spot: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<SpotDto> toggleSpotStatus(@PathVariable int id) {
        try {
            SpotDto updatedSpot = serviceSpot.toggleSpotStatus(id);
            return ResponseEntity.ok(updatedSpot);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Spot status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
