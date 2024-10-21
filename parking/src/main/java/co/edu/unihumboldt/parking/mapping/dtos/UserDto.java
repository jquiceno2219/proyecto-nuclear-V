package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.CarPark;
import co.edu.unihumboldt.parking.domain.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code UserDto} que representa un objeto de transferencia de datos para un usuario.
 * Contiene atributos que describen la información del usuario,
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String userName;
    private String password;
    private String name;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private boolean status;
    private CarPark carPark;
    private Role role;
}
