package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code Schedule} que representa un horario en el sistema.
 * Esta entidad se mapea a la tabla {@code schedules} en el esquema {@code public}.
 * Contiene atributos para el identificador del horario, la hora de inicio,
 * la hora de finalización y el estado (activo/inactivo).
 * Implementa {@code Serializable} para permitir la serialización de objetos.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "schedules",schema = "public", indexes = @Index(columnList = "id"))
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int startTime;
    private int endTime;
    private boolean status;
}
