package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> list();
    RoleDto byId(int id);
    void add(RoleDto t);
    void update(int id, RoleDto roleDto);
    RoleDto toggleRoleStatus(int id);

}
