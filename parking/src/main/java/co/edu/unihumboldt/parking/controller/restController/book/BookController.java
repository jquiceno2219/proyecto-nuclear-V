package co.edu.unihumboldt.parking.controller.restController.book;

import co.edu.unihumboldt.parking.domain.entities.BookStatus;
import co.edu.unihumboldt.parking.domain.entities.Fee;
import co.edu.unihumboldt.parking.domain.entities.PayMethod;
import co.edu.unihumboldt.parking.domain.entities.Spot;
import co.edu.unihumboldt.parking.mapping.dtos.BookDto;
import co.edu.unihumboldt.parking.services.impl.BookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/books")
public class BookController {

    private final BookServiceImpl serviceBook;


    public BookController(BookServiceImpl serviceBook) {
        this.serviceBook = serviceBook;
    }


    @GetMapping("/list")
    public List<BookDto> listBook() {
        return serviceBook.list();
    }

    @PostMapping("/new")
    public ResponseEntity<String> createBook (@RequestParam("name") String name,
                                              @RequestParam("book_date") Date bookDate,
                                              @RequestParam("pay_date") Date payDate,
                                              @RequestParam("start_date") Date startDate,
                                              @RequestParam("end_date") Date endDate,
                                              @RequestParam("book_status") BookStatus bookStatus,
                                              @RequestParam("pay_method") PayMethod payMethod,
                                              @RequestParam("spot") Spot spot,
                                              @RequestParam("fee") Fee fee){
        try {

            BookDto createBook = BookDto.builder()
                    .bookDate(bookDate)
                    .payDate(payDate)
                    .startDate(startDate)
                    .endDate(endDate)
                    .bookStatus(bookStatus)
                    .payMethod(payMethod)
                    .spot(spot)
                    .fee(fee)
                    .build();
            serviceBook.add(createBook);
            return ResponseEntity.ok("Book Created Successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Book: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(
            @PathVariable("id") int id,
            @RequestParam("book_date") Date bookDate,
            @RequestParam("pay_date") Date payDate,
            @RequestParam("start_date") Date startDate,
            @RequestParam("end_date") Date endDate,
            @RequestParam("book_status") BookStatus bookStatus,
            @RequestParam("pay_method") PayMethod payMethod,
            @RequestParam("spot") Spot spot,
            @RequestParam("fee") Fee fee) {

        try {
            BookDto existingBook = serviceBook.byId(id);
            if (existingBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }
            BookDto updateBook = BookDto.builder()
                    .id(id)
                    .bookDate(bookDate)
                    .payDate(payDate)
                    .startDate(startDate)
                    .endDate(endDate)
                    .bookStatus(bookStatus)
                    .payMethod(payMethod)
                    .spot(spot)
                    .fee(fee)
                    .build();

            serviceBook.add(updateBook);
            return ResponseEntity.ok("Book  Updated Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating Book : " + e.getMessage());
        }
    }
}
