package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.Book;
import co.edu.unihumboldt.parking.mapping.dtos.BookDto;
import co.edu.unihumboldt.parking.mapping.mappers.BookMapper;
import co.edu.unihumboldt.parking.repositories.BookRepository;
import co.edu.unihumboldt.parking.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;


    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BookDto> list() {
        List<Book> book = (List<Book>)repository.findAll();
        return BookMapper.mapFromDto(book);
    }

    @Override
    public BookDto byId(int id) {
        Book book = repository.findById(id).orElseThrow();
        return BookMapper.mapFrom(book);
    }

    @Override
    public void add(BookDto t) {
        Book book = BookMapper.mapFrom(t);
        repository.save(book);
    }

    @Override
    public void update(int id, BookDto bookDto) {
        Book book = repository.findById(id).orElseThrow();
        Book updated = BookMapper.mapFrom(bookDto);

        book.setBookDate(updated.getBookDate());
        book.setPayDate(updated.getPayDate());
        book.setStartDate(updated.getStartDate());
        book.setEndDate(updated.getEndDate());
        book.setBookStatus(updated.getBookStatus());
        book.setPayMethod(updated.getPayMethod());
        book.setSpot(updated.getSpot());
        book.setFee(updated.getFee());
    }


}
