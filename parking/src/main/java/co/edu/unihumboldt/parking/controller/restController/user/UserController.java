package co.edu.unihumboldt.parking.controller.restController.user;

import co.edu.unihumboldt.parking.mapping.dtos.UserDto;
import co.edu.unihumboldt.parking.mapping.mappers.CarParkMapper;
import co.edu.unihumboldt.parking.mapping.mappers.RoleMapper;
import co.edu.unihumboldt.parking.services.impl.CarParkServiceImpl;
import co.edu.unihumboldt.parking.services.impl.RoleServiceImpl;
import co.edu.unihumboldt.parking.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("api/users")
public class UserController {

    private final UserServiceImpl serviceUser;
    private final CarParkServiceImpl serviceCarPark;
    private final RoleServiceImpl serviceRole;

    public UserController(UserServiceImpl serviceUser, CarParkServiceImpl serviceCarPark, RoleServiceImpl serviceRole) {
        this.serviceUser = serviceUser;
        this.serviceCarPark = serviceCarPark;
        this.serviceRole = serviceRole;
    }

    @GetMapping("/list")
    public List<UserDto> listUsers() {
        return serviceUser.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createUser (@RequestParam("user_name") String userName,
                                    @RequestParam("password") String password,
                                    @RequestParam("name") String name,
                                    @RequestParam("id_number") String idNumber,
                                    @RequestParam("phone_number") String phoneNumber,
                                    @RequestParam("email") String email,
                                    @RequestParam("status") boolean status,
                                    @RequestParam("car_park")int carParkId,
                                    @RequestParam("role") int roleId){
        try {

            UserDto userDto = UserDto.builder()
                    .userName(userName)
                    .password(password)
                    .name(name)
                    .idNumber(idNumber).phoneNumber(phoneNumber)
                    .email(email)
                    .status(status)
                    .carPark(CarParkMapper.mapFrom(serviceCarPark.byId(carParkId)))
                    .role(RoleMapper.mapFrom(serviceRole.byId(roleId)))
                    .build();
            serviceUser.add(userDto);
            return ResponseEntity.ok("User Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating User: " + e.getMessage());
        }
    }
}
