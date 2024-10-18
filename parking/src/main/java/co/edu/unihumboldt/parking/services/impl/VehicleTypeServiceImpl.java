package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.VehicleType;
import co.edu.unihumboldt.parking.mapping.dtos.VehicleTypeDto;
import co.edu.unihumboldt.parking.mapping.mappers.VehicleTypeMapper;
import co.edu.unihumboldt.parking.repositories.VehicleTypeRepository;
import co.edu.unihumboldt.parking.services.VehicleTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase VehicleTypeServiceImpl implementa la interfaz VehicleTypeService
 * y proporciona la lógica de negocio para gestionar los tipos de vehículos
 * en el sistema. Utiliza VehicleTypeRepository para realizar operaciones CRUD
 * sobre los tipos de vehículos, permitiendo listar todos los tipos, buscar
 * un tipo por su ID, agregar nuevos tipos, actualizar la información de un
 * tipo existente y alternar el estado (activo/inactivo) de un tipo de vehículo.
 * Los datos del tipo de vehículo se mapean a través de VehicleTypeMapper,
 * facilitando la conversión entre entidades de dominio y objetos de transferencia de datos (DTO).
 */

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {
    private final VehicleTypeRepository repository;

    public VehicleTypeServiceImpl(VehicleTypeRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<VehicleTypeDto> list() {
        List<VehicleType> vehicleType = repository.findAll();
        return VehicleTypeMapper.mapFromDto(vehicleType);
    }

    @Override
    public VehicleTypeDto byId(int id) {
        VehicleType vehicleType = repository.findById(id).orElseThrow();
        return VehicleTypeMapper.mapFrom(vehicleType);
    }

    @Override
    public void add(VehicleTypeDto t) {
        VehicleType vehicleType = VehicleTypeMapper.mapFrom(t);
        repository.save(vehicleType);
    }

    @Override
    public void update(int id, VehicleTypeDto vehicleTypeDto) {

        VehicleType vehicleType = repository.findById(id).orElseThrow();
        VehicleType updated = VehicleTypeMapper.mapFrom(vehicleTypeDto);

        vehicleType.setName(updated.getName());
        vehicleType.setStatus(updated.isStatus());
    }

    @Override
    public VehicleTypeDto toggleVehicleTypeStatus(int id) {
        VehicleType vehicleType = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle Type not found with id: " + id));

        vehicleType.setStatus(!vehicleType.isStatus());
        VehicleType savedVehicleType = repository.save(vehicleType);

        return VehicleTypeMapper.mapFrom(savedVehicleType);
    }
}
