package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.AddServiceDto;

import java.util.List;
/**
 * La interfaz AddServiceService define los métodos para gestionar los
 * servicios adicionales en el sistema. Proporciona las operaciones
 * necesarias para interactuar con los servicios adicionales,
 * Los métodos utilizan objetos de transferencia de datos (DTO) para
 * facilitar la manipulación de datos y la separación de la lógica de negocio
 * de la representación de datos.
 */

public interface AddServiceService {
    List<AddServiceDto> list();
    AddServiceDto byId(int id);
    void add(AddServiceDto t);
    void update(int id, AddServiceDto AddServiceDto);
    AddServiceDto toggleAddServiceStatus(int id);
}
