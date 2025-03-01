package com.transport.api.mapper;

import com.transport.api.dto.RegistrationDto;
import com.transport.api.dto.UserDto;
import com.transport.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    @Mapping(target = "payments", ignore = true)
    User convert(UserDto userDto);

    @Mapping(target = "enabled", constant = "true")
    User convert(RegistrationDto userDto);

    @Mapping(target = "payments", ignore = true)
    UserDto convert(User user);
}