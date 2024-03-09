package com.dnd_app.service;

import com.dnd_app.dto.UserDTO;
import com.dnd_app.model.User;
import com.dnd_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDTO> createUser(UserDTO userDTO){
        if (userDTO == null) throw new IllegalArgumentException("User cannot be null");

        Optional<User> userOptional = userRepository.findByUsername(userDTO.getUsername());

        if (userOptional.isPresent()) throw new IllegalArgumentException("User already exists");

        return Optional.of(new UserDTO(userOptional.get()));
    }

}
