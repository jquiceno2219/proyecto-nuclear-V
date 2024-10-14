package co.edu.unihumboldt.parking.controller.restController.carPark;

import co.edu.unihumboldt.parking.mapping.dtos.CarParkDto;
import co.edu.unihumboldt.parking.services.impl.CarParkServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/car-parks")
public class CarParkController {
    private final CarParkServiceImpl serviceCarPark;
    private final Logger logger = LoggerFactory.getLogger(CarParkController.class);
    public CarParkController(CarParkServiceImpl serviceCarPark) {
        this.serviceCarPark = serviceCarPark;
    }

    @GetMapping("/list")
    public List<CarParkDto> listCarPark() {
        return serviceCarPark.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createCarPark (@RequestParam("name") String name,
                                              @RequestParam("address") String adddress,
                                              @RequestParam("phone_number") String phoneNumber,
                                              @RequestParam("nit") String nit,
                                              @RequestParam("coordx") String coordX,
                                              @RequestParam("coordy") String coordY,
                                              @RequestParam("status") boolean status){
        try {

            CarParkDto carParkDto = CarParkDto.builder()
                    .name(name)
                    .address(adddress)
                    .phoneNumber(phoneNumber)
                    .nit(nit)
                    .coordX(coordX)
                    .coordY(coordY)
                    .status(status)
                    .build();
            serviceCarPark.add(carParkDto);
            return ResponseEntity.ok("Car Park Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Car Park: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCarPark(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("address") String adddress,
            @RequestParam("phone_number") String phoneNumber,
            @RequestParam("nit") String nit,
            @RequestParam("coordx") String coordX,
            @RequestParam("coordy") String coordY,
            @RequestParam("status") boolean status) {

        try {
            CarParkDto existingCarpark = serviceCarPark.byId(id);
            if (existingCarpark == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car park not found");
            }
            CarParkDto updateCarpark = CarParkDto.builder()
                    .id(id)
                    .name(name)
                    .address(adddress)
                    .phoneNumber(phoneNumber)
                    .nit(nit)
                    .coordX(coordX)
                    .coordY(coordY)
                    .status(status)
                    .build();

            serviceCarPark.add(updateCarpark);
            return ResponseEntity.ok("Car Park Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Car Park: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<CarParkDto> toggleCarParkStatus(@PathVariable int id) {
        try {
            CarParkDto updatedCarPark = serviceCarPark.toggleCarParkStatus(id);
            return ResponseEntity.ok(updatedCarPark);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Car Park status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
