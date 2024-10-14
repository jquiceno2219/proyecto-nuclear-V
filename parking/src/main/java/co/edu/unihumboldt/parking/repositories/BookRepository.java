package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
