package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code PayMethod} que representa un método de pago en el sistema.
 * Esta entidad se mapea a la tabla {@code pay_methods} en el esquema {@code public}.
 * Contiene atributos para el identificador del método de pago, el nombre del método de pago
 * y el estado (activo/inactivo).
 * Implementa {@code Serializable} para permitir la serialización de objetos.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pay_methods",schema = "public", indexes = @Index(columnList = "id"))
public class PayMethod implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean status;

}
