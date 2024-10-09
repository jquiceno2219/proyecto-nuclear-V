package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> list();
    RoleDto byId(int id);
    void add(RoleDto t);
}
