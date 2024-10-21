package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Clase {@code User} que representa a un usuario en el sistema.
 * Esta entidad se mapea a la tabla {@code users} en el esquema {@code public}.
 * Contiene atributos para el identificador del usuario, nombre de usuario, contraseña,
 * nombre completo, número de identificación, número de teléfono, correo electrónico,
 * estado, parqueadero asociado y rol.
 * Implementa {@code Serializable} para permitir la serialización de objetos.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users",schema = "public", indexes = @Index(columnList = "id"))

public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String password;
    private String name;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private boolean status;
    @ManyToOne
    private CarPark carPark;
    @ManyToOne
    private Role role;

}
