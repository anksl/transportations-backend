package com.transport.service;

import com.transport.api.dto.user.UserDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.exception.UniqueEntityException;
import com.transport.api.mapper.UserMapper;
import com.transport.model.User;
import com.transport.repository.UserRepository;
import com.transport.service.impl.UserServiceImpl;
import com.transport.utils.TestFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.transport.utils.TestFixture.USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final Long ID = 1L;
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @Test
    void testFindById() {
        final Optional<User> user = Optional.of(TestFixture.getUser(ID));
        when(userRepository.findById(ID)).thenReturn(user);
        UserDto userDto = userMapper.convert(user.get());
        UserDto result = userServiceImpl.findById(ID);
        assertThat(result).isEqualTo(userDto);
    }

    @Test
    void testFindByIdWhenNoSuchUser() {
        assertThrows(NoSuchEntityException.class, () -> userServiceImpl.findById(2L));
    }

    @Test
    void testFindByName() {
        final User user = TestFixture.getUser(ID);
        final UserDto userDto = TestFixture.getUserDto(ID);
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findByName(USER_NAME)).thenReturn(users);
        when(userMapper.convert(user)).thenReturn(userDto);
        UserDto result = userServiceImpl.findByName(USER_NAME);
        assertThat(result).isEqualTo(userDto);
    }

    @Test
    void testFindByNameWhenNoUsers() {
        assertThrows(NoSuchEntityException.class, () -> userServiceImpl.findByName(USER_NAME));
    }

    @Test
    void testFindByNameWhenMoreThanOneUser() {
        final User user = TestFixture.getUser(ID);
        final User user1 = TestFixture.getUser(2L);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user);
        when(userRepository.findByName(USER_NAME)).thenReturn(users);
        assertThrows(UniqueEntityException.class, () -> userServiceImpl.findByName(USER_NAME));
    }

    @Test
    void testDelete() {
        userServiceImpl.deleteUser(ID);

        verify(userRepository).deleteById(ID);
    }
}
