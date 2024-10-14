package co.edu.unihumboldt.parking.services;

import co.edu.unihumboldt.parking.mapping.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> list();
    UserDto byId(int id);
    void add(UserDto t);
    void update(int id, UserDto userDto);
    UserDto toggleUserStatus(int id);
}
