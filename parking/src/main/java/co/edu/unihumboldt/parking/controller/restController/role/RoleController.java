package co.edu.unihumboldt.parking.controller.restController.role;

import co.edu.unihumboldt.parking.mapping.dtos.CarParkDto;
import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;
import co.edu.unihumboldt.parking.services.impl.RoleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/roles")
public class RoleController {
    private  final RoleServiceImpl serviceRole;

    public RoleController(RoleServiceImpl serviceRole) {
        this.serviceRole = serviceRole;
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

}
