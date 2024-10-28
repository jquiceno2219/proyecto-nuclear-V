package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code SpotDto} que representa un objeto de transferencia de datos para un espacio .
 * Contiene atributos que describen el identificador del espacio ({@code id}),
 * el número de espacio ({@code spotNumber}),
 * el estado del espacio ({@code status})
 * y la información del parqueadero asociado ({@link CarPark}).
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpotDto{
    private int id;
    private int spotNumber;
    private boolean status;
    private CarPark carPark;
}
