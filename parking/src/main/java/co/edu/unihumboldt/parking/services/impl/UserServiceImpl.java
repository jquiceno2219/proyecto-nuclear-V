package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.User;
import co.edu.unihumboldt.parking.mapping.dtos.UserDto;
import co.edu.unihumboldt.parking.mapping.mappers.UserMapper;
import co.edu.unihumboldt.parking.repositories.UserRepository;
import co.edu.unihumboldt.parking.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserDto> list() {
        List<User> user = (List<User>) repository.findAll();
        return UserMapper.mapFromDto(user);
    }

    @Override
    public UserDto byId(int id) {
        User user = repository.findById(id).orElseThrow();
        return UserMapper.mapFrom(user);
    }

    @Override
    public void add(UserDto t) {
        User user = UserMapper.mapFrom(t);
        repository.save(user);
    }

    @Override
    public void update(int id, UserDto userDto) {
        User user = repository.findById(id).orElseThrow();
        User updated = UserMapper.mapFrom(userDto);

        user.setUserName(updated.getUserName());
        user.setPassword(updated.getPassword());
        user.setName(updated.getName());
        user.setIdNumber(updated.getIdNumber());
        user.setPhoneNumber(updated.getPhoneNumber());
        user.setEmail(updated.getEmail());
        user.setCarPark(updated.getCarPark());
        user.setRole(updated.getRole());
    }

    @Override
    public UserDto toggleUserStatus(int id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setStatus(!user.isStatus());
        User savedUser = repository.save(user);

        return UserMapper.mapFrom(savedUser);
    }


}
