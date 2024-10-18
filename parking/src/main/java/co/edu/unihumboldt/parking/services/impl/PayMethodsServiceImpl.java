package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.PayMethod;
import co.edu.unihumboldt.parking.mapping.dtos.PayMethodDto;
import co.edu.unihumboldt.parking.mapping.mappers.PayMethodMapper;
import co.edu.unihumboldt.parking.repositories.PayMethodRepository;
import co.edu.unihumboldt.parking.services.PayMethodService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase PayMethodsServiceImpl es una implementación del servicio PayMethodService, encargada
 * de gestionar la lógica de negocio relacionada con los métodos de pago en el sistema de
 * estacionamiento. Utiliza el PayMethodRepository para realizar operaciones de acceso a datos,
 * incluyendo listar todos los métodos de pago, buscar un método específico por su ID, agregar
 * nuevos métodos de pago y actualizar métodos existentes. Además, permite alternar el estado
 * de un método de pago, facilitando su activación o desactivación. La clase emplea PayMethodMapper
 * para transformar entre entidades de métodos de pago y objetos de transferencia de datos (DTO),
 * asegurando así una adecuada manipulación y transmisión de la información a través de las capas
 * de la aplicación.
 */

@Service
public class PayMethodsServiceImpl implements PayMethodService {
   private final PayMethodRepository repository;

    public PayMethodsServiceImpl(PayMethodRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PayMethodDto> list() {
        List<PayMethod> payMethod =  repository.findAll();
        return PayMethodMapper.mapFromDto(payMethod);
    }

    @Override
    public PayMethodDto byId(int id) {
        PayMethod payMethod = repository.findById(id).orElseThrow();
        return PayMethodMapper.mapFrom(payMethod);
    }

    @Override
    public void add(PayMethodDto t) {
        PayMethod payMethod = PayMethodMapper.mapFrom(t);
        repository.save(payMethod);
    }

    @Override
    public void update(int id, PayMethodDto payMethodDto) {
        PayMethod payMethod = repository.findById(id).orElseThrow();;
        PayMethod updated = PayMethodMapper.mapFrom(payMethodDto);

        payMethod.setName(updated.getName());
        payMethod.setStatus(updated.isStatus());
    }

    @Override
    public PayMethodDto togglePayMethodStatus(int id) {
        PayMethod payMethod = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pay Method not found with id: " + id));

        payMethod.setStatus(!payMethod.isStatus());
        PayMethod savedPayMethod = repository.save(payMethod);

        return PayMethodMapper.mapFrom(savedPayMethod);
    }
}
