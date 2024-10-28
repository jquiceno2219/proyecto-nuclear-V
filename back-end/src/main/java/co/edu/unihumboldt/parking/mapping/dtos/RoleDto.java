package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase {@code RoleDto} que representa un objeto de transferencia de datos para un rol de usuario.
 * Contiene atributos que describen el nombre del rol y su estado (activo/inactivo).
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Integer id;
    private String name;
    private boolean status;
}
