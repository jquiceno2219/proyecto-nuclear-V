package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code VehicleType} que representa un tipo de vehículo en el sistema.
 * Esta entidad se mapea a la tabla {@code vehicles_type} en el esquema {@code public}.
 * Contiene atributos para el identificador del tipo de vehículo, el nombre y su estado.
 * Implementa {@code Serializable} para permitir la serialización de objetos.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vehicles_type",schema = "public", indexes = @Index(columnList = "id"))
public class VehicleType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean status;
}
