package co.edu.unihumboldt.parking.services.impl;

/**
 * La clase BookStatusServiceImpl es una implementación del servicio BookStatusService, encargada de gestionar
 * la lógica de negocio relacionada con el estado de las reservas en el sistema de estacionamiento.
 * Interactúa con la base de datos mediante la interfaz BookStatusRepository para realizar operaciones como
 * listar todos los estados de las reservas, buscar un estado específico por su ID, agregar un nuevo estado y
 * actualizar la información de un estado existente. Utiliza el BookStatusMapper para convertir entre las
 * entidades de estado del libro y los objetos de transferencia de datos (DTO), asegurando que los datos
 * se manejen de manera eficiente y coherente a lo largo de las diferentes capas de la aplicación.
 */

import co.edu.unihumboldt.parking.domain.entities.BookStatus;
import co.edu.unihumboldt.parking.mapping.dtos.BookStatusDto;
import co.edu.unihumboldt.parking.mapping.mappers.BookStatusMapper;
import co.edu.unihumboldt.parking.repositories.BookStatusRepository;
import co.edu.unihumboldt.parking.services.BookStatusService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookStatusServiceImpl implements BookStatusService {

    private final BookStatusRepository repository;

    public BookStatusServiceImpl(BookStatusRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<BookStatusDto> list() {
        List<BookStatus> bookStatus = repository.findAll();
        return BookStatusMapper.mapFromDto(bookStatus);
    }

    @Override
    public BookStatusDto byId(int id) {
        BookStatus bookStatus = repository.findById(id).orElseThrow();
        return BookStatusMapper.mapFrom(bookStatus);
    }

    @Override
    public void add(BookStatusDto t) {
        BookStatus bookStatus = BookStatusMapper.mapFrom(t);
        repository.save(bookStatus);

    }

    @Override
    public void update(int id, BookStatusDto bookStatusDto) {
        BookStatus bookStatus = repository.findById(id).orElseThrow();
        BookStatus updated = BookStatusMapper.mapFrom(bookStatusDto);

        bookStatus.setName(updated.getName());
        bookStatus.setStatus(updated.isStatus());

    }

    @Override
    public BookStatusDto toggleBookStatus(int id) {
        BookStatus bookStatus = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book Status not found with id: " + id));

        bookStatus.setStatus(!bookStatus.isStatus());
        BookStatus savedBookStatus = repository.save(bookStatus);

        return BookStatusMapper.mapFrom(savedBookStatus);
    }
}
