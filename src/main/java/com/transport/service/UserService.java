package com.transport.service;

import com.transport.api.dto.user.RegistrationUserDto;
import com.transport.api.dto.user.UpdateUserDto;
import com.transport.api.dto.user.UserDto;
import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto findByName(String name);

    UserDto getCurrentUser();

    void createUser(RegistrationUserDto user);

    UpdateUserDto updateUser(Long id, UpdateUserDto newUser);

    void deleteUser(Long id);
}
