package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.BookStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStatusRepository extends CrudRepository<BookStatus, Integer> {
}
