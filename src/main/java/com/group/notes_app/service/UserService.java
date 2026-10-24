package com.group.notes_app.service;

import com.group.notes_app.dto.NoteDto;
import com.group.notes_app.dto.UserDto;
import com.group.notes_app.dto.UserLoginRequest;
import com.group.notes_app.dto.UserRegistrationRequest;
import com.group.notes_app.entity.Note;
import com.group.notes_app.entity.User;
import com.group.notes_app.exceptions.UsernameAlreadyExistsException;
import com.group.notes_app.repository.UserRepository;
import com.group.notes_app.security.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtUtils jwtUtils;

    private UserDto convertToNoteDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(this::convertToNoteDto)
                .collect(Collectors.toList());
        return userDtos;
    }

    public UserDto getUserById(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(foundUser, userDto);
        return userDto;
    }

    public UserDto createUser(UserRegistrationRequest user) {
        checkIfUsernameExists(user.getUsername());
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(newUser);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(savedUser, userDto);
        userDto.setToken(jwtUtils.generateToken(userDto.getUsername()));
        return userDto;
    }

    public String loginUser(UserLoginRequest user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UsernameNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    public void checkIfUsernameExists(String username) {
        if(userRepository.existsUserByUsername(username)) {
            throw new UsernameAlreadyExistsException("Username: " + username + " already exists");
        }
    }
}
