package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.ServiceBookDto;

import java.util.List;

public interface ServiceBookService {
    List<ServiceBookDto> list();
    ServiceBookDto byId(int id);
    void add(ServiceBookDto t);
    void update(int id, ServiceBookDto ServiceBookDto);
}
