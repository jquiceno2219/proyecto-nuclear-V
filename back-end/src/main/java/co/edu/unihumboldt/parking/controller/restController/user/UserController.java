package co.edu.unihumboldt.parking.controller.restController.user;

import co.edu.unihumboldt.parking.mapping.dtos.UserDto;
import co.edu.unihumboldt.parking.services.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code UserController} que gestiona las operaciones CRUD para los
 * usuarios a través de la API. Utiliza {@code UserServiceImpl} para interactuar
 * con la lógica de negocio. Proporciona endpoints para listar, crear, actualizar
 * y alternar el estado de los usuarios. Los métodos manejan excepciones y devuelven
 * respuestas adecuadas, incluyendo códigos de estado HTTP que reflejan el resultado
 * de las operaciones.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserServiceImpl serviceUser;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/list")
    public List<UserDto> listUsers() {
        return serviceUser.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createUser (@RequestBody UserDto userDto){
        try {
            serviceUser.add(userDto);
            return ResponseEntity.ok("User Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating User: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable("id") int id,
            @RequestBody UserDto userDto) {

        try {
            UserDto existingUser = serviceUser.byId(id);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            userDto.setId(id);
            serviceUser.add(userDto);
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
