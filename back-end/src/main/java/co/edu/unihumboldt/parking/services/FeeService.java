package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.FeeDto;

import java.util.List;

public interface FeeService {
    List<FeeDto> list();
    FeeDto byId(int id);
    void add(FeeDto t);
    void update(int id, FeeDto feeDto);
    FeeDto toggleFeeStatus(int id);
}
