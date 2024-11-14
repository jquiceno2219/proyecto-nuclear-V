package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.ParkingFacility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code ParkingSpotDto} que representa un objeto de transferencia de datos para un espacio .
 * Contiene atributos que describen el identificador del espacio ({@code id}),
 * el número de espacio ({@code spotNumber}),
 * el estado del espacio ({@code status})
 * y la información del parqueadero asociado ({@link ParkingFacility}).
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotDto {
    private int id;
    private int spotNumber;
    private boolean status;
    private ParkingFacility parkingFacility;
}
