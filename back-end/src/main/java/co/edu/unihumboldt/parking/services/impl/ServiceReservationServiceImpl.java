package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.ServiceReservation;
import co.edu.unihumboldt.parking.mapping.dtos.ServiceReservationDto;
import co.edu.unihumboldt.parking.mapping.mappers.ServiceReservationMapper;
import co.edu.unihumboldt.parking.repositories.ServiceBookRepository;
import co.edu.unihumboldt.parking.services.ServiceReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase ServiceReservationServiceImpl implementa la interfaz ServiceReservationService y se encarga de gestionar
 * la lógica de negocio relacionada con los servicios adicionales asociados a las reservas en el sistema
 * de estacionamiento. Utiliza ServiceBookRepository para acceder a los datos, permitiendo la obtención
 * de todos los registros de servicios adicionales, la búsqueda de un servicio específico por su ID, y
 * la adición de nuevos registros de servicios adicionales. Además, proporciona la funcionalidad de
 * actualización de registros existentes, asegurando que los cambios se reflejen adecuadamente en la
 * base de datos. La conversión entre entidades de servicio adicional y objetos de transferencia de datos
 * (DTO) se realiza a través de ServiceReservationMapper, facilitando la manipulación y transmisión de la
 * información en las distintas capas de la aplicación.
 */

@Service
public class ServiceReservationServiceImpl implements ServiceReservationService {
    private final ServiceBookRepository repository;

    public ServiceReservationServiceImpl(ServiceBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ServiceReservationDto> list() {
        List<ServiceReservation> serviceReservation = repository.findAll();
        return ServiceReservationMapper.mapFromDto(serviceReservation);
    }

    @Override
    public ServiceReservationDto byId(int id) {
        ServiceReservation serviceReservation = repository.findById(id).orElseThrow();
        return ServiceReservationMapper.mapFrom(serviceReservation);
    }

    @Override
    public void add(ServiceReservationDto t) {
        ServiceReservation serviceReservation = ServiceReservationMapper.mapFrom(t);
        repository.save(serviceReservation);
    }

    @Override
    public void update(int id, ServiceReservationDto serviceReservationDto) {
        ServiceReservation serviceReservation = repository.findById(id).orElseThrow();
        ServiceReservation updated = ServiceReservationMapper.mapFrom(serviceReservationDto);

        serviceReservation.setReservation(updated.getReservation());
        serviceReservation.setAddService(updated.getAddService());
    }

}
