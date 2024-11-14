package co.edu.unihumboldt.parking.controller.restController.service;

import co.edu.unihumboldt.parking.mapping.dtos.AddServiceDto;
import co.edu.unihumboldt.parking.services.impl.AddServiceServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code AddServiceController} que gestiona las operaciones CRUD para
 * los servicios adicionales a través de la API. Utiliza {@code AddServiceServiceImpl}
 * para acceder a la lógica de negocio. Proporciona endpoints para listar, crear,
 * actualizar y alternar el estado de los servicios adicionales. Los métodos manejan
 * excepciones y devuelven respuestas adecuadas en caso de éxito o error,
 * registrando errores para facilitar la depuración.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/add-services")
public class AddServiceController {

    private final AddServiceServiceImpl serviceAddService;

    private final Logger logger = LoggerFactory.getLogger(AddServiceController.class);

    @GetMapping("/list")
    public List<AddServiceDto> listAddServices() {
        return serviceAddService.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createAddService (@RequestBody AddServiceDto addServiceDto){
        try {
            serviceAddService.add(addServiceDto);
            return ResponseEntity.ok("Add a Service Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating AddService: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAddService(
            @PathVariable("id") int id,
            @RequestBody AddServiceDto addServiceDto) {

        try {
            AddServiceDto existingAddService = serviceAddService.byId(id);
            if (existingAddService == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AddService not found");
            }
            addServiceDto.setId(id);
            serviceAddService.add(addServiceDto);
            return ResponseEntity.ok("AddService Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Add Service: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<AddServiceDto> toggleAddServiceStatus(@PathVariable int id) {
        try {
            AddServiceDto updatedAddService = serviceAddService.toggleAddServiceStatus(id);
            return ResponseEntity.ok(updatedAddService);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating AddService status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
