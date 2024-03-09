package com.dnd_app.security.service;

import com.dnd_app.dto.UserDTO;
import com.dnd_app.model.User;
import com.dnd_app.model.Salt;
import com.dnd_app.repository.SaltRepository;
import com.dnd_app.repository.UserRepository;
import com.dnd_app.security.payload.request.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final SaltRepository saltRepository;

    public Optional<UserDTO> login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        User user = optionalUser.orElseThrow();
        Salt salt = saltRepository.findByUser(user).orElseThrow();

        String pw = loginRequest.getPassword();
        String hashedPw = BCrypt.hashpw(pw, salt.getValue());

        if (!hashedPw.equals(user.getPassword()))
            return Optional.empty();

        return Optional.of(UserDTO.userDTOBuilder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .password(user.getPassword())
                .build());
    }
}
