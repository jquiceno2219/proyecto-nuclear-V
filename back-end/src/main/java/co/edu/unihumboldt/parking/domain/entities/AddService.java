package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code AddService} que representa un servicio adicional ofrecido en un parqueadero de
 * automóviles. Esta entidad se mapea a la tabla {@code days_schedule} en el esquema
 * {@code public}. Contiene atributos para el identificador del servicio, nombre, precio,
 * estado y la relación con un {@code CarPark}. Implementa {@code Serializable} para
 * permitir la serialización de objetos. Utiliza Lombok para generar constructores,
 * métodos de acceso y un constructor de estilo "builder".
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "days_schedule",schema = "public", indexes = @Index(columnList = "id"))
public class AddService implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    private boolean status;
    @ManyToOne
    private ParkingFacility parkingFacility;

}
