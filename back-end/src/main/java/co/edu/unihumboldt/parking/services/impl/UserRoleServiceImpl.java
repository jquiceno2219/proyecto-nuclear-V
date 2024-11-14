package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.UserRole;
import co.edu.unihumboldt.parking.mapping.dtos.UserRoleDto;
import co.edu.unihumboldt.parking.mapping.mappers.UserRoleMapper;
import co.edu.unihumboldt.parking.repositories.UserRoleRepository;
import co.edu.unihumboldt.parking.services.UserRoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase UserRoleServiceImpl es una implementación del servicio UserRoleService, encargada de gestionar
 * la lógica de negocio relacionada con los roles de usuario en el sistema de estacionamiento. Utiliza
 * el UserRoleRepository para realizar operaciones de acceso a datos, incluyendo la obtención de todos los
 * roles, la búsqueda de un rol específico por su ID, la adición de nuevos roles y la actualización de
 * roles existentes. Además, permite alternar el estado de un rol, facilitando su activación o
 * desactivación. La clase emplea UserRoleMapper para transformar entre entidades de rol y objetos de
 * transferencia de datos (DTO), asegurando así una adecuada manipulación y transmisión de la
 * información a través de las capas de la aplicación.
 */

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository repository;

    public UserRoleServiceImpl(UserRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserRoleDto> list() {
        List<UserRole> userRole =  repository.findAll();
        return UserRoleMapper.mapFromDto(userRole);
    }

    @Override
    public UserRoleDto byId(int id) {
        UserRole userRole = repository.findById(id).orElseThrow();
        return UserRoleMapper.mapFrom(userRole);
    }

    @Override
    public void add(UserRoleDto t) {
        UserRole userRole = UserRoleMapper.mapFrom(t);
        repository.save(userRole);
    }

    @Override
    public void update(int id, UserRoleDto userRoleDto) {
        UserRole userRole = repository.findById(id).orElseThrow();;
        UserRole updated = UserRoleMapper.mapFrom(userRoleDto);

        userRole.setName(updated.getName());
        userRole.setStatus(updated.isStatus());
    }

    @Override
    public UserRoleDto toggleRoleStatus(int id) {
        UserRole userRole = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserRole not found with id: " + id));

        userRole.setStatus(!userRole.isStatus());
        UserRole savedUserRole = repository.save(userRole);

        return UserRoleMapper.mapFrom(savedUserRole);
    }
}
