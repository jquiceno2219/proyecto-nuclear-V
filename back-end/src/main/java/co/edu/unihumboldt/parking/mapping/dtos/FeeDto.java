package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.ParkingFacility;
import co.edu.unihumboldt.parking.domain.entities.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code FeeDto} que representa un objeto de transferencia de datos para una tarifa.
 * Contiene atributos que describen el nombre de la tarifa, su precio, estado (activo/inactivo),
 * el parque de estacionamiento asociado y el tipo de vehículo relacionado.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeeDto {
    private int id;
    private String name;
    private int price;
    private boolean status;
    private ParkingFacility parkingFacility;
    private VehicleType vehicleType;
}
