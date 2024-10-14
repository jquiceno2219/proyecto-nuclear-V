package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.PayMethod;
import co.edu.unihumboldt.parking.mapping.dtos.PayMethodDto;
import co.edu.unihumboldt.parking.mapping.mappers.PayMethodMapper;
import co.edu.unihumboldt.parking.repositories.PayMethodRepository;
import co.edu.unihumboldt.parking.services.PayMethodService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PayMethodsServiceImpl implements PayMethodService {
   private final PayMethodRepository repository;

    public PayMethodsServiceImpl(PayMethodRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PayMethodDto> list() {
        List<PayMethod> payMethod = (List<PayMethod>) repository.findAll();
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
