package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code CarParkDto} que representa un objeto de transferencia de datos para un parque de vehículos.
 * Contiene atributos que describen el parque, incluyendo su identificador, nombre, dirección,
 * número de teléfono, NIT, coordenadas (X, Y) y estado (activo o inactivo).
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingFacilityDto {
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String nit;
    private String coordX;
    private String coordY;
    private boolean status;
}
