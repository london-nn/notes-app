package com.group.notes_app.service;

import com.group.notes_app.dto.UserDto;
import com.group.notes_app.dto.UserLoginRequest;
import com.group.notes_app.dto.UserRegistrationRequest;
import com.group.notes_app.entity.User;
import com.group.notes_app.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public UserDto getUserById(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(foundUser, userDto);
        return userDto;
    }

    public UserDto createUser(UserRegistrationRequest user) {
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        User savedUser = userRepository.save(newUser);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(savedUser, userDto);
        return userDto;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
