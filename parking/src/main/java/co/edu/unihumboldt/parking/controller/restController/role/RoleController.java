package co.edu.unihumboldt.parking.controller.restController.role;

import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;
import co.edu.unihumboldt.parking.services.impl.RoleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/roles")
public class RoleController {
    private  final RoleServiceImpl serviceRole;
    private final Logger logger = LoggerFactory.getLogger(RoleController.class);
    public RoleController(RoleServiceImpl serviceRole) {
        this.serviceRole = serviceRole;
    }

    @GetMapping("/list")
    public List<RoleDto> listRole() {
        return serviceRole.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createRole (@RequestParam("name") String name,
                                                 @RequestParam("status") boolean status){
        try {

            RoleDto roleDto = RoleDto.builder()
                    .name(name)
                    .status(status)
                    .build();
            serviceRole.add(roleDto);
            return ResponseEntity.ok("Role Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Role: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRole(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("status") boolean status) {

        try {
            RoleDto existingRole = serviceRole.byId(id);
            if (existingRole == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
            }
            RoleDto updateRole = RoleDto.builder()
                    .id(id)
                    .name(name)
                    .status(status)
                    .build();

            serviceRole.add(updateRole);
            return ResponseEntity.ok("Role Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Role: " + e.getMessage());
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
