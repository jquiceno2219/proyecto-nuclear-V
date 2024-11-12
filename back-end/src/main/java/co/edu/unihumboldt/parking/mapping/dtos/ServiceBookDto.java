package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.AddService;
import co.edu.unihumboldt.parking.domain.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code ServiceBookDto} que representa un objeto de transferencia de datos para un servicio asociado a una reserva.
 * Contiene atributos que describen el identificador del registro, la reserva relacionada ({@link Reservation})
 * y el servicio adicional asociado ( {@link AddService}).
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceBookDto {
    private int id;
    private Reservation reservation;
    private AddService addService;
}
