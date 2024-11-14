package co.edu.unihumboldt.parking.controller.restController.parkingSpot;

import co.edu.unihumboldt.parking.mapping.dtos.ParkingSpotDto;
import co.edu.unihumboldt.parking.services.impl.ParkingSpotServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code ParkingSpotController} que gestiona las operaciones CRUD para los
 * spots a través de la API. Utiliza {@code ParkingSpotServiceImpl} para interactuar
 * con la lógica de negocio. Proporciona endpoints para listar, crear, actualizar
 * y alternar el estado de los spots. Los métodos manejan excepciones y devuelven
 * respuestas adecuadas, incluyendo códigos de estado HTTP para reflejar el resultado
 * de las operaciones.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/parking-spots")
public class ParkingSpotController {
    private final ParkingSpotServiceImpl serviceSpot;

    private final Logger logger = LoggerFactory.getLogger(ParkingSpotController.class);

    @GetMapping("/list")
    public List<ParkingSpotDto> listSpots() {
        return serviceSpot.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createSpot (@RequestBody ParkingSpotDto parkingSpotDto){
        try {
            serviceSpot.add(parkingSpotDto);
            return ResponseEntity.ok("ParkingSpot Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating ParkingSpot: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSpot(
            @PathVariable("id") int id,
            @RequestBody ParkingSpotDto parkingSpotDto) {

        try {
            ParkingSpotDto existingSpot = serviceSpot.byId(id);
            if (existingSpot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ParkingSpot not found");
            }
            parkingSpotDto.setId(id);
            serviceSpot.add(parkingSpotDto);
            return ResponseEntity.ok("ParkingSpot Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating ParkingSpot: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ParkingSpotDto> toggleSpotStatus(@PathVariable int id) {
        try {
            ParkingSpotDto updatedSpot = serviceSpot.toggleSpotStatus(id);
            return ResponseEntity.ok(updatedSpot);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating ParkingSpot status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
