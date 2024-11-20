package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.DayOfWeek;
import co.edu.unihumboldt.parking.domain.entities.ParkingFacility;
import co.edu.unihumboldt.parking.domain.entities.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code DayScheduleDto} que representa un objeto de transferencia de datos para un horario de días.
 * Contiene atributos que describen el horario asociado, el día de la semana y el parque de vehículos correspondiente.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayScheduleDto {
       private int id;
       private Schedule schedule;
       private DayOfWeek dayOfWeek;
       private ParkingFacility parkingFacility;

}
