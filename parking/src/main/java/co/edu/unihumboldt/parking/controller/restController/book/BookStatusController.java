package co.edu.unihumboldt.parking.controller.restController.book;

import co.edu.unihumboldt.parking.mapping.dtos.BookStatusDto;
import co.edu.unihumboldt.parking.services.impl.BookStatusServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/books-status")
public class BookStatusController {
    private final BookStatusServiceImpl serviceBookStatus;
    private final Logger logger = LoggerFactory.getLogger(BookStatusController.class);

    public BookStatusController(BookStatusServiceImpl serviceBookStatus) {
        this.serviceBookStatus = serviceBookStatus;
    }


    @GetMapping("/list")
    public List<BookStatusDto> listBookStatus() {
        return serviceBookStatus.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createBookStatus (@RequestParam("name") String name,
                                              @RequestParam("status") boolean status){
        try {

            BookStatusDto createBookStatus = BookStatusDto.builder()
                    .name(name)
                    .status(status)
                    .build();
            serviceBookStatus.add(createBookStatus);
            return ResponseEntity.ok("Book Status Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Book Status: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBookStatus(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("status") boolean status) {

        try {
            BookStatusDto existingBookStatus = serviceBookStatus.byId(id);
            if (existingBookStatus == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book Status not found");
            }
            BookStatusDto updateBookStatus = BookStatusDto.builder()
                    .id(id)
                    .name(name)
                    .status(status)
                    .build();

            serviceBookStatus.add(updateBookStatus);
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
