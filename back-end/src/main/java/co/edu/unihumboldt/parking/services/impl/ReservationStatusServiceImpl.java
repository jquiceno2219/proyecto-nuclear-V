package co.edu.unihumboldt.parking.services.impl;

/**
 * La clase ReservationStatusServiceImpl es una implementación del servicio ReservationStatusService, encargada de gestionar
 * la lógica de negocio relacionada con el estado de las reservas en el sistema de estacionamiento.
 * Interactúa con la base de datos mediante la interfaz ReservationStatusRepository para realizar operaciones como
 * listar todos los estados de las reservas, buscar un estado específico por su ID, agregar un nuevo estado y
 * actualizar la información de un estado existente. Utiliza el ReservationStatusMapper para convertir entre las
 * entidades de estado del libro y los objetos de transferencia de datos (DTO), asegurando que los datos
 * se manejen de manera eficiente y coherente a lo largo de las diferentes capas de la aplicación.
 */

import co.edu.unihumboldt.parking.domain.entities.ReservationStatus;
import co.edu.unihumboldt.parking.mapping.dtos.ReservationStatusDto;
import co.edu.unihumboldt.parking.mapping.mappers.ReservationStatusMapper;
import co.edu.unihumboldt.parking.repositories.ReservationStatusRepository;
import co.edu.unihumboldt.parking.services.ReservationStatusService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationStatusServiceImpl implements ReservationStatusService {

    private final ReservationStatusRepository repository;

    public ReservationStatusServiceImpl(ReservationStatusRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<ReservationStatusDto> list() {
        List<ReservationStatus> reservationStatuses = repository.findAll();
        return ReservationStatusMapper.mapFromDto(reservationStatuses);
    }

    @Override
    public ReservationStatusDto byId(int id) {
        ReservationStatus reservationStatus = repository.findById(id).orElseThrow();
        return ReservationStatusMapper.mapFrom(reservationStatus);
    }

    @Override
    public void add(ReservationStatusDto t) {
        ReservationStatus reservationStatus = ReservationStatusMapper.mapFrom(t);
        repository.save(reservationStatus);

    }

    @Override
    public void update(int id, ReservationStatusDto reservationStatusDto) {
        ReservationStatus reservationStatus = repository.findById(id).orElseThrow();
        ReservationStatus updated = ReservationStatusMapper.mapFrom(reservationStatusDto);

        reservationStatus.setName(updated.getName());
        reservationStatus.setStatus(updated.isStatus());

    }

    @Override
    public ReservationStatusDto toggleBookStatus(int id) {
        ReservationStatus reservationStatus = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation Status not found with id: " + id));

        reservationStatus.setStatus(!reservationStatus.isStatus());
        ReservationStatus savedReservationStatus = repository.save(reservationStatus);

        return ReservationStatusMapper.mapFrom(savedReservationStatus);
    }
}
