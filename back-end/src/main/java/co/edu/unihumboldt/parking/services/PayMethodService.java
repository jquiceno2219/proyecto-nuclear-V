package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.PayMethodDto;

import java.util.List;

public interface PayMethodService {
    List<PayMethodDto> list();
    PayMethodDto byId(int id);
    void add(PayMethodDto t);
    void update(int id, PayMethodDto payMethodDto);
    PayMethodDto togglePayMethodStatus(int id);
}
