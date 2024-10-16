package co.edu.unihumboldt.parking.controller.restController.fee;

import co.edu.unihumboldt.parking.mapping.dtos.FeeDto;
import co.edu.unihumboldt.parking.mapping.mappers.CarParkMapper;
import co.edu.unihumboldt.parking.mapping.mappers.VehicleTypeMapper;
import co.edu.unihumboldt.parking.services.impl.CarParkServiceImpl;
import co.edu.unihumboldt.parking.services.impl.FeeServiceImpl;
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
@RequestMapping("api/fee")
public class FeeController {
    private final FeeServiceImpl serviceFee;
    private final CarParkServiceImpl serviceCarPark;
    private final VehicleTypeServiceImpl serviceVehicleType;
    private final Logger logger = LoggerFactory.getLogger(FeeController.class);

    public FeeController(FeeServiceImpl serviceFee, CarParkServiceImpl serviceCarPark, VehicleTypeServiceImpl serviceVehicleType) {
        this.serviceFee = serviceFee;
        this.serviceCarPark = serviceCarPark;
        this.serviceVehicleType = serviceVehicleType;
    }


    @GetMapping("/list")
    public List<FeeDto> listFees() {
        return serviceFee.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createFee (@RequestParam("name") String name,
                                              @RequestParam("price") int price,
                                              @RequestParam("status") boolean status,
                                              @RequestParam("car_park")int carParkId,
                                              @RequestParam("vehicle_type") int vehicleTypeId){
        try {

            FeeDto feeDto = FeeDto.builder()
                    .name(name)
                    .price(price)
                    .status(status)
                    .carPark(CarParkMapper.mapFrom(serviceCarPark.byId(carParkId)))
                    .vehicleType(VehicleTypeMapper.mapFrom(serviceVehicleType.byId(vehicleTypeId)))
                    .build();
            serviceFee.add(feeDto);
            return ResponseEntity.ok("Fee Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Fee: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFee(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("status") boolean status,
            @RequestParam("car_park")int carParkId,
            @RequestParam("vehicle_type") int vehicleTypeId) {

        try {
            FeeDto existingFee = serviceFee.byId(id);
            if (existingFee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fee not found");
            }
            FeeDto updateFee = FeeDto.builder()
                    .id(id)
                    .name(name)
                    .price(price)
                    .status(status)
                    .carPark(CarParkMapper.mapFrom(serviceCarPark.byId(carParkId)))
                    .vehicleType(VehicleTypeMapper.mapFrom(serviceVehicleType.byId(vehicleTypeId)))
                    .build();

            serviceFee.add(updateFee);
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
