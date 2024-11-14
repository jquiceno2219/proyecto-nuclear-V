package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code CarPark} que representa un parqueadero en el sistema.
 * Esta entidad se mapea a la tabla {@code car_parks} en el esquema {@code public}.
 * Contiene atributos para el identificador del parqueadero, su nombre, dirección,
 * número de teléfono, NIT (Número de Identificación Tributaria), coordenadas X e Y
 * y un indicador de si el parqueadero está activo o no.
 * Implementa {@code Serializable} para permitir la serialización de objetos.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "parking_facilities",schema = "public", indexes = @Index(columnList = "id"))
public class ParkingFacility implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String nit;
    private String coordX;
    private String coordY;
    private boolean status;
}
