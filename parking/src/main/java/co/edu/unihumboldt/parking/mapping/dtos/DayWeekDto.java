package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code DayWeekDto} que representa un objeto de transferencia de datos para un día de la semana.
 * Contiene atributos que describen el número del día de la semana, el título del día y su estado (activo/inactivo).
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayWeekDto{
    private int id;
    private int dayWeekNumber;
    private String dayTitle;
    private boolean status;
}
