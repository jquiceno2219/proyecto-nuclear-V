package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.UserRole;
import co.edu.unihumboldt.parking.mapping.dtos.UserRoleDto;

import java.util.List;

public class UserRoleMapper {
    public static UserRoleDto mapFrom(UserRole source){
        return UserRoleDto.builder()
                .id(source.getId())
                .name(source.getName())
                .status(source.isStatus())
                .build();
    }

    public static UserRole mapFrom(UserRoleDto source){
        return UserRole.builder()
                .id(source.getId())
                .name(source.getName())
                .status(source.isStatus())
                .build();
    }

    public static List<UserRole> mapFrom(List<UserRoleDto> source){
        return source.parallelStream()
                .map(UserRoleMapper::mapFrom)
                .toList();

    }
    public static List<UserRoleDto> mapFromDto(List<UserRole> source){
        return source.parallelStream()
                .map(UserRoleMapper::mapFrom)
                .toList();

    }
}
