package ru.vk.education.job.mapper;

import ru.vk.education.job.domain.User;
import ru.vk.education.job.web.dto.UserDto;

import java.util.List;

public class UserMapper {

    public static User dtoToDomain(UserDto userDto) {
        return new User(userDto.name(), userDto.skills(), userDto.experience());
    }

    public static UserDto domainToDto(User user) {
        return new UserDto(user.name(), user.skills(), user.experience());
    }

    public static List<UserDto> domainListToDtoList(List<User> users) {
        return users.stream()
                .map(UserMapper::domainToDto)
                .toList();
    }
}
