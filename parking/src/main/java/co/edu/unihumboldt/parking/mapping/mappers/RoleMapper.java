package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.Role;
import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;

import java.util.List;

public class RoleMapper {
    public static RoleDto mapFrom(Role source){
        return new RoleDto(source.getId(),
                source.getName(),
                source.isStatus());
    }

    public static Role mapFrom(RoleDto source){
        return new Role(source.id(),
                source.name(),
                source.status());
    }

    public static List<Role> mapFrom(List<RoleDto> source){
        return source.parallelStream()
                .map(RoleMapper::mapFrom)
                .toList();

    }
    public static List<RoleDto> mapFromDto(List<Role> source){
        return source.parallelStream()
                .map(RoleMapper::mapFrom)
                .toList();

    }
}
