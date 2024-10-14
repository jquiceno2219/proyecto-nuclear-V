package co.edu.unihumboldt.parking.controller.restController.vehicleType;

import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;
import co.edu.unihumboldt.parking.mapping.dtos.VehicleTypeDto;
import co.edu.unihumboldt.parking.services.impl.VehicleTypeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("api/vehicles-types")
public class VehicleTypeController {
    private  final VehicleTypeServiceImpl serviceVehicleType;
    private final Logger logger = LoggerFactory.getLogger(VehicleTypeController.class);

    public VehicleTypeController(VehicleTypeServiceImpl serviceVehicleType) {
        this.serviceVehicleType = serviceVehicleType;
    }


    @GetMapping("/list")
    public List<VehicleTypeDto> listVehicleType() {
        return serviceVehicleType.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createVehicleType (@RequestParam("name") String name,
                                              @RequestParam("status") boolean status){
        try {

            VehicleTypeDto vehicleTypeDto = VehicleTypeDto.builder()
                    .name(name)
                    .status(status)
                    .build();
            serviceVehicleType.add(vehicleTypeDto);
            return ResponseEntity.ok("Vehicle Type Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Vehicle Type: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVehicleType(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("status") boolean status) {

        try {
            VehicleTypeDto existingVehicleType = serviceVehicleType.byId(id);
            if (existingVehicleType == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle Type not found");
            }
            VehicleTypeDto updateVehicleType = VehicleTypeDto.builder()
                    .id(id)
                    .name(name)
                    .status(status)
                    .build();

            serviceVehicleType.add(updateVehicleType);
            return ResponseEntity.ok("Vehicle Type Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Vehicle type: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<VehicleTypeDto> toggleVehicleTypeStatus(@PathVariable int id) {
        try {
            VehicleTypeDto updatedVehicleType = serviceVehicleType.toggleVehicleTypeStatus(id);
            return ResponseEntity.ok(updatedVehicleType);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Vehicle Type status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
