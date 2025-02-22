package com.transport.service.impl;

import com.transport.api.dto.UserDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.exception.UniqueEntityException;
import com.transport.api.mapper.UserMapper;
import com.transport.model.Payment;
import com.transport.model.User;
import com.transport.repository.UserRepository;
import com.transport.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto findById(Long id) {
        return userMapper.convert(userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("User with id: %s doesn't exist", id))));
    }

    @Override
    public UserDto findByName(String name) {
        List<User> users = userRepository.findByName(name);
        if (users.size() == 0)
            throw new NoSuchEntityException(String.format("User with name: %s doesn't exist", name));
        else if (users.size() > 1)
            throw new UniqueEntityException(String.format("More than one user with name: %s", name));
        return userMapper.convert(users.get(0));
    }

    @Override
    public UserDto getCurrentUser() {
        return findByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Transactional
    @Override
    public void createUser(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userMapper.convert(user));
    }

    @Transactional
    @Override
    public UserDto updateUser(Long id, UserDto newUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("User with id: %s doesn't exist", id)));
        User newUser = userMapper.convert(newUserDto);
        user.setName(newUser.getName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setEmail(newUser.getEmail());
        setRating(user);
        user.setEnabled(newUser.getEnabled());
        user.setRoles(newUser.getRoles());
        return userMapper.convert(userRepository.save(user));
    }

    private void setRating(User user) {
        short rating = 0;
        if (!user.getPayments().isEmpty()) {
            for (Payment payment : user.getPayments()) {
                rating += payment.getPaymentStatus().getVal();
            }
        }
        user.setRating(rating);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
