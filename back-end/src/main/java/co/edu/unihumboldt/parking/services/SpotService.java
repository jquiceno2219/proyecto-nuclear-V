package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.SpotDto;

import java.util.List;

public interface SpotService {
    List<SpotDto> list();
    SpotDto byId(int id);
    void add(SpotDto t);
    void update(int id, SpotDto spotDto);
    SpotDto toggleSpotStatus(int id);
}
