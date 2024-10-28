package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.Role;
import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;
import co.edu.unihumboldt.parking.mapping.mappers.RoleMapper;
import co.edu.unihumboldt.parking.repositories.RoleRepository;
import co.edu.unihumboldt.parking.services.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La clase RoleServiceImpl es una implementación del servicio RoleService, encargada de gestionar
 * la lógica de negocio relacionada con los roles de usuario en el sistema de estacionamiento. Utiliza
 * el RoleRepository para realizar operaciones de acceso a datos, incluyendo la obtención de todos los
 * roles, la búsqueda de un rol específico por su ID, la adición de nuevos roles y la actualización de
 * roles existentes. Además, permite alternar el estado de un rol, facilitando su activación o
 * desactivación. La clase emplea RoleMapper para transformar entre entidades de rol y objetos de
 * transferencia de datos (DTO), asegurando así una adecuada manipulación y transmisión de la
 * información a través de las capas de la aplicación.
 */

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RoleDto> list() {
        List<Role> role =  repository.findAll();
        return RoleMapper.mapFromDto(role);
    }

    @Override
    public RoleDto byId(int id) {
        Role role = repository.findById(id).orElseThrow();
        return RoleMapper.mapFrom(role);
    }

    @Override
    public void add(RoleDto t) {
        Role role = RoleMapper.mapFrom(t);
        repository.save(role);
    }

    @Override
    public void update(int id, RoleDto roleDto) {
        Role role = repository.findById(id).orElseThrow();;
        Role updated = RoleMapper.mapFrom(roleDto);

        role.setName(updated.getName());
        role.setStatus(updated.isStatus());
    }

    @Override
    public RoleDto toggleRoleStatus(int id) {
        Role role = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        role.setStatus(!role.isStatus());
        Role savedRole = repository.save(role);

        return RoleMapper.mapFrom(savedRole);
    }
}
