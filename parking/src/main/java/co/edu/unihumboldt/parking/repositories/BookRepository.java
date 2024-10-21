package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
