package co.edu.unihumboldt.parking.mapping.mappers;


import co.edu.unihumboldt.parking.domain.entities.User;
import co.edu.unihumboldt.parking.mapping.dtos.UserDto;

import java.util.List;


public class UserMapper {
    public static UserDto mapFrom(User source){
        return  UserDto.builder()
                .id(source.getId())
                .userName(source.getUserName())
                .password(source.getPassword())
                .name(source.getName())
                .idNumber(source.getIdNumber())
                .phoneNumber(source.getPhoneNumber())
                .email(source.getEmail())
                .status(source.isStatus())
                .carPark(source.getCarPark())
                .role(source.getRole())
                .build();
    }

    public static User mapFrom(UserDto source){
        return new User(source.getId(),
                source.getUserName(),
                source.getPassword(),
                source.getName(),
                source.getIdNumber(),
                source.getPhoneNumber(),
                source.getEmail(),
                source.isStatus(),
                source.getCarPark(),
                source.getRole());
    }

    public static List<User> mapFrom(List<UserDto> source){
        return source.parallelStream()
                .map(UserMapper::mapFrom)
                .toList();

    }
    public static List<UserDto> mapFromDto(List<User> source){
        return source.parallelStream()
                .map(UserMapper::mapFrom)
                .toList();

    }
}
