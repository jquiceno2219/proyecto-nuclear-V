package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStatusRepository extends JpaRepository<BookStatus, Integer> {
}
