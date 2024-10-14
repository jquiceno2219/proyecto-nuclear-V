package co.edu.unihumboldt.parking.controller.restController.user;

import co.edu.unihumboldt.parking.mapping.dtos.UserDto;
import co.edu.unihumboldt.parking.mapping.mappers.CarParkMapper;
import co.edu.unihumboldt.parking.mapping.mappers.RoleMapper;
import co.edu.unihumboldt.parking.services.impl.CarParkServiceImpl;
import co.edu.unihumboldt.parking.services.impl.RoleServiceImpl;
import co.edu.unihumboldt.parking.services.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/users")
public class UserController {

    private final UserServiceImpl serviceUser;
    private final CarParkServiceImpl serviceCarPark;
    private final RoleServiceImpl serviceRole;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

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

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable("id") int id,
            @RequestParam("user_name") String userName,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("id_number") String idNumber,
            @RequestParam("phone_number") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam("status") boolean status,
            @RequestParam("car_park")int carParkId,
            @RequestParam("role") int roleId) {

        try {
            UserDto existingUser = serviceUser.byId(id);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            UserDto updateUser = UserDto.builder()
                    .id(id)
                    .userName(userName)
                    .password(password)
                    .name(name)
                    .idNumber(idNumber).phoneNumber(phoneNumber)
                    .email(email)
                    .status(status)
                    .carPark(CarParkMapper.mapFrom(serviceCarPark.byId(carParkId)))
                    .role(RoleMapper.mapFrom(serviceRole.byId(roleId)))
                    .build();

            serviceUser.add(updateUser);
            return ResponseEntity.ok("User Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating User: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<UserDto> toggleUserStatus(@PathVariable int id) {
        try {
            UserDto updatedUser = serviceUser.toggleUserStatus(id);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating user status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
