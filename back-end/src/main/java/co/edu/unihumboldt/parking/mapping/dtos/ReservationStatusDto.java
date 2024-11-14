package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code ReservationStatusDto} que representa un objeto de transferencia de datos para el estado de una reserva.
 * Contiene atributos que describen el estado de la reserva, incluyendo su identificador,
 * nombre y estado (activo o inactivo).
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationStatusDto {
    private int id;
    private String name;
    private boolean status;
}
