package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.UserRoleDto;

import java.util.List;

public interface UserRoleService {
    List<UserRoleDto> list();
    UserRoleDto byId(int id);
    void add(UserRoleDto t);
    void update(int id, UserRoleDto userRoleDto);
    UserRoleDto toggleRoleStatus(int id);

}
