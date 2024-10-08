package co.edu.unihumboldt.parking.mapping.mappers;


import co.edu.unihumboldt.parking.domain.entities.User;
import co.edu.unihumboldt.parking.mapping.dtos.UserDto;
import lombok.Builder;

import java.util.List;

@Builder
public class UserMapper {
    public static UserDto mapFrom(User source){
        return new UserDto(source.getId(),
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

    public static User mapFrom(UserDto source){
        return new User(source.id(),
                source.userName(),
                source.password(),
                source.name(),
                source.idNumber(),
                source.phoneNumber(),
                source.email(),
                source.status(),
                source.carPark(),
                source.role());
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
