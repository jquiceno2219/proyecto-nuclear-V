package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code PayMethodDto} que representa un objeto de transferencia de datos para un método de pago.
 * Contiene atributos que describen el nombre del método de pago y su estado (activo/inactivo).
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayMethodDto {
    private int id;
    private String name;
    private boolean status;
}
