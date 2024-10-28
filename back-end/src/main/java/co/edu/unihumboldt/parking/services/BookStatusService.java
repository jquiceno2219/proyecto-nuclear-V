package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.BookStatusDto;

import java.util.List;

public interface BookStatusService {
    List<BookStatusDto> list();
    BookStatusDto byId(int id);
    void add(BookStatusDto t);
    void update(int id, BookStatusDto bookStatus);
    BookStatusDto toggleBookStatus(int id);
}
