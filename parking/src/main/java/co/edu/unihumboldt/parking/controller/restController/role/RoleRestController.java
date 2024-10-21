package co.edu.unihumboldt.parking.controller.restController.role;

import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;
import co.edu.unihumboldt.parking.services.impl.RoleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code RoleRestController} que gestiona las operaciones CRUD para
 * los roles a través de la API. Utiliza {@code RoleServiceImpl} para acceder
 * a la lógica de negocio. Proporciona endpoints para listar, crear, actualizar
 * y alternar el estado de los roles. Los métodos manejan excepciones y
 * devuelven respuestas adecuadas en caso de éxito o error, además de registrar
 * errores para facilitar la depuración.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/roles")
public class RoleRestController {
    private  final RoleServiceImpl serviceRole;
    private final Logger logger = LoggerFactory.getLogger(RoleRestController.class);
    @GetMapping("/list")
    public List<RoleDto> listRole() {
        return serviceRole.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createRole (@RequestBody RoleDto roleDto){
        try {
            serviceRole.add(roleDto);
            return ResponseEntity.ok("Role Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Role: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRole(@PathVariable("id") int id, @RequestBody RoleDto roleDto) {
        try {
            RoleDto existingRole = serviceRole.byId(id);
            if (existingRole == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
            }
            roleDto.setId(id);
            serviceRole.add(roleDto);
            return ResponseEntity.ok("Role Updated Successfully");
        } catch (Exception e) {
            logger.error("Error updating Role: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating Role: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<RoleDto> toggleRoleStatus(@PathVariable int id) {
        try {
            RoleDto updatedRole = serviceRole.toggleRoleStatus(id);
            return ResponseEntity.ok(updatedRole);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Role status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
