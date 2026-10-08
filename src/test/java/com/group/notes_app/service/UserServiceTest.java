package com.group.notes_app.service;

import com.group.notes_app.dto.UserDto;
import com.group.notes_app.entity.User;
import com.group.notes_app.repository.UserRepository;
import com.group.notes_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUserByIdTest_Success(){
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("username");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());

        verify(userRepository,times(1)).findById(userId);
    }

    @Test
    public void getUserByIdTest_NotFound(){
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, ()->userService.getUserById(userId));

        assertEquals("Пользователь не найден", exception.getMessage());

        verify(userRepository,times(1)).findById(userId);
    }

    @Test
    public void getAllUsersTest_Success(){
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        List<User> users = new ArrayList<>();
        users.add(user);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user1 : users){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user1,userDto);
            userDtos.add(userDto);
        }
        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(users.size(), result.size());
        assertEquals(userDtos.get(0), result.get(0));

        verify(userRepository,times(1)).findAll();
    }

}