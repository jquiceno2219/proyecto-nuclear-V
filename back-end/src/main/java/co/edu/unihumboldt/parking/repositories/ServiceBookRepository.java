package co.edu.unihumboldt.parking.repositories;

import co.edu.unihumboldt.parking.domain.entities.ServiceBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceBookRepository extends JpaRepository<ServiceBook, Integer> {
}
