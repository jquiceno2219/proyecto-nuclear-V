package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.Role;
import co.edu.unihumboldt.parking.mapping.dtos.RoleDto;

import java.util.List;

public class RoleMapper {
    public static RoleDto mapFrom(Role source){
        return RoleDto.builder()
                .id(source.getId())
                .name(source.getName())
                .status(source.isStatus())
                .build();
    }

    public static Role mapFrom(RoleDto source){
        return Role.builder()
                .id(source.getId())
                .name(source.getName())
                .status(source.isStatus())
                .build();
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
