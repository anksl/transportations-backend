package com.transport.service;

import com.transport.api.dto.UserDto;

public interface UserService {

    UserDto findById(Long id);

    UserDto findByName(String name);

    UserDto getCurrentUser();

    void createUser(UserDto user);

    UserDto updateUser(Long id, UserDto newUser);

    void deleteUser(Long id);
}
