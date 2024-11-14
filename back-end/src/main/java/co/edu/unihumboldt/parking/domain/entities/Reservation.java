package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * Clase {@code Reservation} que representa una reserva en el sistema. Esta entidad se mapea
 * a la tabla {@code books} en el esquema {@code public}. Contiene atributos para el
 * identificador de la reserva, fechas relevantes (fecha de reserva, fecha de pago,
 * fecha de inicio y fecha de finalización), y relaciones con otras entidades, como
 * el estado de la reserva, el método de pago, el lugar de la reserva y la tarifa.
 * Implementa {@code Serializable} para permitir la serialización de objetos. Utiliza
 * Lombok para generar constructores, métodos de acceso y un constructor de estilo
 * "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reservations",schema = "public", indexes = @Index(columnList = "id"))
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date bookDate;
    private Date payDate;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    private ReservationStatus reservationStatus;
    @ManyToOne
    private PayMethod payMethod;
    @ManyToOne
    private ParkingSpot parkingSpot;
    @ManyToOne
    private Fee fee;
}
