package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.Reservation;
import co.edu.unihumboldt.parking.mapping.dtos.ReservationDto;
import co.edu.unihumboldt.parking.mapping.mappers.ReservationMapper;
import co.edu.unihumboldt.parking.repositories.ReservationRepository;
import co.edu.unihumboldt.parking.services.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase ReservationServiceImpl es una implementación del servicio ReservationService, responsable de manejar la lógica
 * de negocio relacionada con las reservas en el sistema de estacionamiento. Esta clase interactúa
 * con la base de datos a través de la interfaz ReservationRepository, permitiendo realizar operaciones como listar
 * todas las reservas, buscar una reserva por su ID, agregar una nueva reserva y actualizar la información de una reserva
 * existente. Utiliza el ReservationMapper para convertir entre las entidades y los objetos de transferencia de datos
 * (DTO), asegurando que los datos se gestionen correctamente entre las diferentes capas de la aplicación.
 */

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository repository;


    public ReservationServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ReservationDto> list() {
        List<Reservation> reservation = repository.findAll();
        return ReservationMapper.mapFromDto(reservation);
    }

    @Override
    public ReservationDto byId(int id) {
        Reservation reservation = repository.findById(id).orElseThrow();
        return ReservationMapper.mapFrom(reservation);
    }

    @Override
    public void add(ReservationDto t) {
        Reservation reservation = ReservationMapper.mapFrom(t);
        repository.save(reservation);
    }

    @Override
    public void update(int id, ReservationDto reservationDto) {
        Reservation reservation = repository.findById(id).orElseThrow();
        Reservation updated = ReservationMapper.mapFrom(reservationDto);

        reservation.setBookDate(updated.getBookDate());
        reservation.setPayDate(updated.getPayDate());
        reservation.setStartDate(updated.getStartDate());
        reservation.setEndDate(updated.getEndDate());
        reservation.setReservationStatus(updated.getReservationStatus());
        reservation.setPayMethod(updated.getPayMethod());
        reservation.setParkingSpot(updated.getParkingSpot());
        reservation.setFee(updated.getFee());
    }


}
