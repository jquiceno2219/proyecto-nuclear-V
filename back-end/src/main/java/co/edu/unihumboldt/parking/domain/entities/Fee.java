package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code Fee} que representa una tarifa asociada a un tipo de vehículo en un parqueo.
 * Esta entidad se mapea a la tabla {@code fees} en el esquema {@code public}.
 * Contiene atributos para el identificador de la tarifa, el nombre de la tarifa,
 * el precio de la tarifa, el estado (activo/inactivo), el parqueo asociado y el tipo de vehículo asociado.
 * Implementa {@code Serializable} para permitir la serialización de objetos.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "fees",schema = "public", indexes = @Index(columnList = "id"))
public class Fee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    private boolean status;
    @ManyToOne
    private ParkingFacility parkingFacility;
    @ManyToOne
    private VehicleType vehicleType;
}
