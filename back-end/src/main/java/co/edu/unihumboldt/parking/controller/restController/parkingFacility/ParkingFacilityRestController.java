package co.edu.unihumboldt.parking.controller.restController.parkingFacility;

import co.edu.unihumboldt.parking.mapping.dtos.ParkingFacilityDto;
import co.edu.unihumboldt.parking.services.impl.ParkingFacilityServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code ParkingFacilityRestController} que gestiona las operaciones CRUD para
 * los estacionamientos a través de la API. Utiliza {@code CarParkServiceImpl}
 * para acceder a la lógica de negocio. Proporciona endpoints para listar, crear,
 * actualizar y alternar el estado de los estacionamientos.
 * Los métodos manejan excepciones y devuelven respuestas adecuadas en caso de
 * éxito o error, incluyendo registros de errores para facilitar la depuración.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/parking-facilities")
public class ParkingFacilityRestController {
    private final ParkingFacilityServiceImpl serviceCarPark;
    private final Logger logger = LoggerFactory.getLogger(ParkingFacilityRestController.class);

    @GetMapping("/list")
    public List<ParkingFacilityDto> listCarPark() {
        return serviceCarPark.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createCarPark (@RequestBody ParkingFacilityDto parkingFacilityDto){
        try {
            serviceCarPark.add(parkingFacilityDto);
            return ResponseEntity.ok("Car Park Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Car Park: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCarPark(
            @PathVariable("id") int id,
            @RequestBody ParkingFacilityDto parkingFacilityDto) {

        try {
            ParkingFacilityDto existingCarpark = serviceCarPark.byId(id);
            if (existingCarpark == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car park not found");
            }
            parkingFacilityDto.setId(id);
            serviceCarPark.add(parkingFacilityDto);
            return ResponseEntity.ok("Car Park Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Car Park: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ParkingFacilityDto> toggleCarParkStatus(@PathVariable int id) {
        try {
            ParkingFacilityDto updatedCarPark = serviceCarPark.toggleCarParkStatus(id);
            return ResponseEntity.ok(updatedCarPark);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Car Park status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
