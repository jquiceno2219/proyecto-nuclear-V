package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.AddService;
import co.edu.unihumboldt.parking.mapping.dtos.AddServiceDto;
import co.edu.unihumboldt.parking.mapping.mappers.AddServiceMapper;
import co.edu.unihumboldt.parking.repositories.AddServiceRepository;
import co.edu.unihumboldt.parking.services.AddServiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase AddServiceServiceImpl es una implementación del servicio AddServiceService que maneja la lógica
 * de negocio relacionada con los servicios adicionales en el sistema de estacionamiento. Esta clase se encarga
 * de interactuar con la base de datos a través de la interfaz AddServiceRepository, permitiendo realizar
 * operaciones como listar todos los servicios adicionales, buscar un servicio por su ID, agregar un nuevo
 * servicio, actualizar un servicio existente y alternar el estado de un servicio adicional. Utiliza el
 * AddServiceMapper para convertir entre entidades y objetos de transferencia de datos (DTO), asegurando
 * que los datos se manejen de manera eficiente entre las diferentes capas de la aplicación.
 */

@Service
public class AddServiceServiceImpl implements AddServiceService {
    
    private final AddServiceRepository repository;

    public AddServiceServiceImpl(AddServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AddServiceDto> list() {
        List<AddService> addService = repository.findAll();
        return AddServiceMapper.mapFromDto(addService);
    }

    @Override
    public AddServiceDto byId(int id) {
        AddService addService = repository.findById(id).orElseThrow();
        return AddServiceMapper.mapFrom(addService);
    }

    @Override
    public void add(AddServiceDto t) {
        AddService addService = AddServiceMapper.mapFrom(t);
        repository.save(addService);
    }

    @Override
    public void update(int id, AddServiceDto addServiceDto) {
        AddService addService = repository.findById(id).orElseThrow();
        AddService updated = AddServiceMapper.mapFrom(addServiceDto);

        addService.setName(updated.getName());
        addService.setPrice(updated.getPrice());
        addService.setStatus(updated.isStatus());
        addService.setCarPark(updated.getCarPark());
    }

    @Override
    public AddServiceDto toggleAddServiceStatus(int id) {
        AddService addService = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AddService not found with id: " + id));

        addService.setStatus(!addService.isStatus());
        AddService savedAddService = repository.save(addService);

        return AddServiceMapper.mapFrom(savedAddService);
    }
}
