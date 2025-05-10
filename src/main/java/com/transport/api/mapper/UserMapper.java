package com.transport.api.mapper;

import com.transport.api.dto.user.RegistrationUserDto;
import com.transport.api.dto.user.UpdateUserDto;
import com.transport.api.dto.user.UserDto;
import com.transport.model.User;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    @Mapping(target = "payments", ignore = true)
    User convert(UserDto userDto);

    List<UserDto> convert(List<User> user);

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "about", source = "about")
    @Mapping(target = "roles", source = "roles")
    void toUser(@MappingTarget User entity, UpdateUserDto individual);

    @Mapping(target = "enabled", constant = "true")
    User convert(RegistrationUserDto userDto);

    @Mapping(target = "payments", ignore = true)
    UserDto convert(User user);

    UpdateUserDto toUpdateUserDto(User user);
}