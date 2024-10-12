package co.edu.unihumboldt.parking.controller.restController.carPark;

import co.edu.unihumboldt.parking.mapping.dtos.CarParkDto;
import co.edu.unihumboldt.parking.mapping.dtos.UserDto;
import co.edu.unihumboldt.parking.mapping.mappers.CarParkMapper;
import co.edu.unihumboldt.parking.mapping.mappers.RoleMapper;
import co.edu.unihumboldt.parking.services.impl.CarParkServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("api/car_parks")
public class CarParkController {
    private final CarParkServiceImpl serviceCarPark;

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
}
