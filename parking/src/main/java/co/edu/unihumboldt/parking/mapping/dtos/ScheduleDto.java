package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code ScheduleDto} que representa un objeto de transferencia de datos para un horario.
 * Contiene atributos que describen el identificador del horario, la hora de inicio, la hora de fin y el estado (activo/inactivo).
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private int id;
    private int startTime;
    private int endTime;
    private boolean status;
}
