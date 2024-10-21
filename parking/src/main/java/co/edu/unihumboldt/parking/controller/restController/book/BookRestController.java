package co.edu.unihumboldt.parking.controller.restController.book;

import co.edu.unihumboldt.parking.mapping.dtos.BookDto;
import co.edu.unihumboldt.parking.services.impl.BookServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase {@code BookRestController} que gestiona las operaciones CRUD para reservas
 * a través de la API. Utiliza {@code BookServiceImpl} para acceder a la lógica
 * de negocio. Proporciona endpoints para listar reservas, crear una nueva reserva
 * y actualizar una reserva existente. Los métodos manejan excepciones y devuelven
 * respuestas adecuadas en caso de éxito o error.
 */

@RestController
@AllArgsConstructor
@RequestMapping("api/books")
public class BookRestController {

    private final BookServiceImpl serviceBook;

    @GetMapping("/list")
    public List<BookDto> listBook() {
        return serviceBook.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createBook (@RequestBody BookDto bookDto){
        try {

            serviceBook.add(bookDto);
            return ResponseEntity.ok("Book Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Book: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(
            @PathVariable("id") int id,
            @RequestBody BookDto bookDto) {

        try {
            BookDto existingBook = serviceBook.byId(id);
            if (existingBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }
            bookDto.setId(id);
            serviceBook.add(bookDto);
            return ResponseEntity.ok("Book  Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Book : " + e.getMessage());
        }
    }
}
