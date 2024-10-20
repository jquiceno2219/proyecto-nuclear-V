package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.ServiceBook;
import co.edu.unihumboldt.parking.mapping.dtos.ServiceBookDto;
import co.edu.unihumboldt.parking.mapping.mappers.ServiceBookMapper;
import co.edu.unihumboldt.parking.repositories.ServiceBookRepository;
import co.edu.unihumboldt.parking.services.ServiceBookService;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase ServiceBookServiceImpl implementa la interfaz ServiceBookService y se encarga de gestionar
 * la lógica de negocio relacionada con los servicios adicionales asociados a las reservas en el sistema
 * de estacionamiento. Utiliza ServiceBookRepository para acceder a los datos, permitiendo la obtención
 * de todos los registros de servicios adicionales, la búsqueda de un servicio específico por su ID, y
 * la adición de nuevos registros de servicios adicionales. Además, proporciona la funcionalidad de
 * actualización de registros existentes, asegurando que los cambios se reflejen adecuadamente en la
 * base de datos. La conversión entre entidades de servicio adicional y objetos de transferencia de datos
 * (DTO) se realiza a través de ServiceBookMapper, facilitando la manipulación y transmisión de la
 * información en las distintas capas de la aplicación.
 */

@Service
public class ServiceBookServiceImpl implements ServiceBookService {
    private final ServiceBookRepository repository;

    public ServiceBookServiceImpl(ServiceBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ServiceBookDto> list() {
        List<ServiceBook> serviceBook = repository.findAll();
        return ServiceBookMapper.mapFromDto(serviceBook);
    }

    @Override
    public ServiceBookDto byId(int id) {
        ServiceBook serviceBook = repository.findById(id).orElseThrow();
        return ServiceBookMapper.mapFrom(serviceBook);
    }

    @Override
    public void add(ServiceBookDto t) {
        ServiceBook serviceBook = ServiceBookMapper.mapFrom(t);
        repository.save(serviceBook);
    }

    @Override
    public void update(int id, ServiceBookDto serviceBookDto) {
        ServiceBook serviceBook = repository.findById(id).orElseThrow();
        ServiceBook updated = ServiceBookMapper.mapFrom(serviceBookDto);

        serviceBook.setBook(updated.getBook());
        serviceBook.setAddService(updated.getAddService());
    }

}
