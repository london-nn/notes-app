package com.group.notes_app.service;

import com.group.notes_app.entity.User;
import com.group.notes_app.repository.UserRepository;
import com.group.notes_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());

        verify(userRepository,times(1)).findById(userId);
    }

}