package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code DayOfWeek} que representa un día de la semana en el sistema.
 * Esta entidad se mapea a la tabla {@code days_week} en el esquema {@code public}.
 * Contiene atributos para el identificador del día de la semana, el número del día de la semana,
 * el título del día y el estado (activo/inactivo).
 * Implementa {@code Serializable} para permitir la serialización de objetos.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "days_week",schema = "public", indexes = @Index(columnList = "id"))
public class DayOfWeek implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dayWeekNumber;
    private String dayTitle;
    private boolean status;
}
