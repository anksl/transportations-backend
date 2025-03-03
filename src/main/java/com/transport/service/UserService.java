package com.transport.service;

import com.transport.api.dto.user.RegistrationUserDto;
import com.transport.api.dto.user.UpdateUserDto;
import com.transport.api.dto.user.UserDto;

public interface UserService {

    UserDto findById(Long id);

    UserDto findByName(String name);

    UserDto getCurrentUser();

    void createUser(RegistrationUserDto user);

    UpdateUserDto updateUser(Long id, UpdateUserDto newUser);

    void deleteUser(Long id);
}
