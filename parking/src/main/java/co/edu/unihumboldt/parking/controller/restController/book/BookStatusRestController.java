package co.edu.unihumboldt.parking.controller.restController.book;

import co.edu.unihumboldt.parking.mapping.dtos.BookStatusDto;
import co.edu.unihumboldt.parking.services.impl.BookStatusServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase {@code BookStatusRestController} que gestiona las operaciones CRUD para
 * el estado de las reservas a través de la API. Utiliza {@code BookStatusServiceImpl}
 * para acceder a la lógica de negocio. Proporciona endpoints para listar, crear
 * y actualizar el estado de las reservas, así como para alternar su estado.
 * Los métodos manejan excepciones y devuelven respuestas adecuadas en caso de
 * éxito o error, incluyendo registros de errores para la depuración.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/books-status")
public class BookStatusRestController {
    private final BookStatusServiceImpl serviceBookStatus;
    private final Logger logger = LoggerFactory.getLogger(BookStatusRestController.class);


    @GetMapping("/list")
    public List<BookStatusDto> listBookStatus() {
        return serviceBookStatus.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createBookStatus (@RequestBody BookStatusDto bookStatusDto){
        try {

            serviceBookStatus.add(bookStatusDto);
            return ResponseEntity.ok("Book Status Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Book Status: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBookStatus(
            @PathVariable("id") int id,
            @RequestBody BookStatusDto bookStatusDto) {

        try {
            BookStatusDto existingBookStatus = serviceBookStatus.byId(id);
            if (existingBookStatus == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book Status not found");
            }
            bookStatusDto.setId(id);
            serviceBookStatus.add(bookStatusDto);
            return ResponseEntity.ok("Book Status Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Book Status: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<BookStatusDto> toggleBookStatus(@PathVariable int id) {
        try {
            BookStatusDto updatedBookStatus = serviceBookStatus.toggleBookStatus(id);
            return ResponseEntity.ok(updatedBookStatus);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating Book status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
