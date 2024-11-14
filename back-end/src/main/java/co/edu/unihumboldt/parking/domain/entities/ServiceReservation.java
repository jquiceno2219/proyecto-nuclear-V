package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code ServiceReservation} que representa la relación entre un servicio adicional
 * y una reserva en el sistema. Esta entidad se mapea a la tabla {@code services_book}
 * en el esquema {@code public}. Contiene atributos para el identificador de la relación,
 * la reserva asociada y el servicio adicional asociado.
 * Implementa {@code Serializable} para permitir la serialización de objetos.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "services_reservation",schema = "public",indexes = @Index(columnList = "id"))
public class ServiceReservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Reservation reservation;
    @ManyToOne
    private AddService addService;

}
