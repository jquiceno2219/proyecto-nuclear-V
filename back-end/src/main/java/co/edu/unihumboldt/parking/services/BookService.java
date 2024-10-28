package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> list();
    BookDto byId(int id);
    void add(BookDto t);
    void update(int id, BookDto bookDto);

}
