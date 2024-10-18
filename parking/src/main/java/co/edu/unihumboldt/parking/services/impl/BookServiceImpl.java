package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.Book;
import co.edu.unihumboldt.parking.mapping.dtos.BookDto;
import co.edu.unihumboldt.parking.mapping.mappers.BookMapper;
import co.edu.unihumboldt.parking.repositories.BookRepository;
import co.edu.unihumboldt.parking.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase BookServiceImpl es una implementación del servicio BookService, responsable de manejar la lógica
 * de negocio relacionada con las reservas en el sistema de estacionamiento. Esta clase interactúa
 * con la base de datos a través de la interfaz BookRepository, permitiendo realizar operaciones como listar
 * todas las reservas, buscar una reserva por su ID, agregar una nueva reserva y actualizar la información de una reserva
 * existente. Utiliza el BookMapper para convertir entre las entidades y los objetos de transferencia de datos
 * (DTO), asegurando que los datos se gestionen correctamente entre las diferentes capas de la aplicación.
 */

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;


    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BookDto> list() {
        List<Book> book = repository.findAll();
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
