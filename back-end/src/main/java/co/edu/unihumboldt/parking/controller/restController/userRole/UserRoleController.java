package co.edu.unihumboldt.parking.controller.restController.userRole;

import co.edu.unihumboldt.parking.mapping.dtos.UserRoleDto;
import co.edu.unihumboldt.parking.services.impl.UserRoleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code UserRoleController} que gestiona las operaciones CRUD para
 * los roles a través de la API. Utiliza {@code UserRoleServiceImpl} para acceder
 * a la lógica de negocio. Proporciona endpoints para listar, crear, actualizar
 * y alternar el estado de los roles. Los métodos manejan excepciones y
 * devuelven respuestas adecuadas en caso de éxito o error, además de registrar
 * errores para facilitar la depuración.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/user-roles")
public class UserRoleController {
    private  final UserRoleServiceImpl serviceRole;
    private final Logger logger = LoggerFactory.getLogger(UserRoleController.class);
    @GetMapping("/list")
    public List<UserRoleDto> listRole() {
        return serviceRole.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createRole (@RequestBody UserRoleDto userRoleDto){
        try {
            serviceRole.add(userRoleDto);
            return ResponseEntity.ok("UserRole Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating UserRole: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRole(@PathVariable("id") int id, @RequestBody UserRoleDto userRoleDto) {
        try {
            UserRoleDto existingRole = serviceRole.byId(id);
            if (existingRole == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserRole not found");
            }
            userRoleDto.setId(id);
            serviceRole.add(userRoleDto);
            return ResponseEntity.ok("UserRole Updated Successfully");
        } catch (Exception e) {
            logger.error("Error updating UserRole: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating UserRole: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<UserRoleDto> toggleRoleStatus(@PathVariable int id) {
        try {
            UserRoleDto updatedRole = serviceRole.toggleRoleStatus(id);
            return ResponseEntity.ok(updatedRole);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating UserRole status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
