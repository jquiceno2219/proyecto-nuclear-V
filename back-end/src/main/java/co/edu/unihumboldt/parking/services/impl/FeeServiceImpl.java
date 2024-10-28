package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.Fee;
import co.edu.unihumboldt.parking.mapping.dtos.FeeDto;
import co.edu.unihumboldt.parking.mapping.mappers.FeeMapper;
import co.edu.unihumboldt.parking.repositories.FeeRepository;
import co.edu.unihumboldt.parking.services.FeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase FeeServiceImpl es una implementación del servicio FeeService, responsable de manejar
 * la lógica de negocio relacionada con las tarifas en el sistema de estacionamiento. Utiliza
 * el FeeRepository para realizar operaciones de acceso a datos, como listar todas las tarifas,
 * buscar tarifas por ID, agregar nuevas tarifas y actualizar tarifas existentes. También permite
 * alternar el estado de una tarifa, facilitando su activación o desactivación. La clase se apoya
 * en FeeMapper para convertir entre entidades de tarifas y objetos de transferencia de datos (DTO),
 * garantizando así una correcta manipulación y transferencia de información a través de las capas
 * de la aplicación.
 */

@Service
public class FeeServiceImpl implements FeeService {
    private final FeeRepository repository;


    public FeeServiceImpl(FeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FeeDto> list() {
        List<Fee> fee =  repository.findAll();
        return FeeMapper.mapFromDto(fee);
    }

    @Override
    public FeeDto byId(int id) {
        Fee fee = repository.findById(id).orElseThrow();
        return FeeMapper.mapFrom(fee);
    }

    @Override
    public void add(FeeDto t) {
        Fee fee = FeeMapper.mapFrom(t);
        repository.save(fee);
    }

    @Override
    public void update(int id, FeeDto feeDto) {
        Fee fee = repository.findById(id).orElseThrow();
        Fee updated = FeeMapper.mapFrom(feeDto);

        fee.setName(updated.getName());
        fee.setPrice(updated.getPrice());
        fee.setStatus(updated.isStatus());
        fee.setCarPark(updated.getCarPark());
        fee.setVehicleType(updated.getVehicleType());
    }

    @Override
    public FeeDto toggleFeeStatus(int id) {
        Fee fee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fee not found with id: " + id));

        fee.setStatus(!fee.isStatus());
        Fee savedFee = repository.save(fee);

        return FeeMapper.mapFrom(savedFee);
    }
}
