package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code AddServiceDto} que representa un objeto de transferencia de datos para un servicio adicional.
 * Contiene atributos que describen el servicio adicional, incluyendo su identificador, nombre, precio, estado
 * y la referencia al parqueo asociado.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddServiceDto {
    private int id;
    private String name;
    private int price;
    private boolean status;
    private CarPark carPark;
}
