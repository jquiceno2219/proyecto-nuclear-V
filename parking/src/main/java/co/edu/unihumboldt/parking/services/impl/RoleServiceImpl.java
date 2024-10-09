package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.Role;
import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;
import co.edu.unihumboldt.parking.mapping.mappers.RoleMapper;
import co.edu.unihumboldt.parking.repositories.RoleRepository;
import co.edu.unihumboldt.parking.services.RoleService;
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
}
