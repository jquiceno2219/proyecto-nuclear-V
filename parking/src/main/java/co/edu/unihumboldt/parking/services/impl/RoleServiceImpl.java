package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.Role;
import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;
import co.edu.unihumboldt.parking.mapping.mappers.RoleMapper;
import co.edu.unihumboldt.parking.repositories.RoleRepository;
import co.edu.unihumboldt.parking.services.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RoleDto> list() {
        List<Role> role = (List<Role>) repository.findAll();
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
