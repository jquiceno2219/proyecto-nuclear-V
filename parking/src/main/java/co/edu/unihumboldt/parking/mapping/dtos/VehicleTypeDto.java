package co.edu.unihumboldt.parking.mapping.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * La clase VehicleTypeDto representa un objeto de transferencia de datos que encapsula la información
 * sobre un tipo de vehículo en el sistema. Contiene un identificador único (id), un nombre que describe
 * el tipo de vehículo (name) y un estado booleano (status) que indica si el tipo de vehículo está activo
 * o no. Esta clase se utiliza para facilitar la comunicación entre la capa de presentación y la lógica
 * de negocio, permitiendo que se transfieran los datos de manera eficiente.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypeDto {
    private int id;
    private String name;
    private boolean status;
}
