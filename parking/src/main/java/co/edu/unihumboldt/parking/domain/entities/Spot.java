package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code Spot} que representa un espacio  en un parqueadero.
 * Esta entidad se mapea a la tabla {@code spots} en el esquema {@code public}.
 * Contiene atributos para el identificador del espacio, el número de espacio y su estado.
 * Implementa {@code Serializable} para permitir la serialización de objetos.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "spots",schema = "public", indexes = @Index(columnList = "id"))
public class Spot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int spotNumber;
    private boolean status;
    @ManyToOne
    private CarPark carPark;

}
