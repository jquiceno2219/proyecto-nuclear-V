package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code DaySchedule} que representa un horario diario en el sistema.
 * Esta entidad se mapea a la tabla {@code days_schedule} en el esquema {@code public}.
 * Contiene atributos para el identificador del horario diario y relaciones con
 * otras entidades: {@code Schedule}, {@code DayWeek} y {@code CarPark}.
 * Implementa {@code Serializable} para permitir la serialización de objetos.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "days_schedule", schema = "public", indexes = @Index(columnList = "id"))
public class DaySchedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Schedule schedule;
    @ManyToOne
    private DayWeek dayWeek;
    @ManyToOne
    private ParkingFacility parkingFacility;
}
