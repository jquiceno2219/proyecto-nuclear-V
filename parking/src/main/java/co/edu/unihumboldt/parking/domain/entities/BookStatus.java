package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code BookStatus} que representa el estado de una reserva en el sistema.
 * Esta entidad se mapea a la tabla {@code books_status} en el esquema {@code public}.
 * Contiene atributos para el identificador del estado, el nombre del estado y
 * un indicador de si el estado está activo o no. Implementa {@code Serializable}
 * para permitir la serialización de objetos. Utiliza Lombok para generar
 * constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books_status",schema = "public", indexes = @Index(columnList = "id"))
public class BookStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean status;

}
