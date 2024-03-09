package com.dnd_app.controller;


import com.dnd_app.dto.UserDTO;
import com.dnd_app.repository.UserRepository;
import com.dnd_app.security.jwt.JwtManipulator;
import com.dnd_app.security.payload.request.LoginRequest;
import com.dnd_app.security.jwt.TimedJwt;
import com.dnd_app.security.service.AuthenticationService;
import com.dnd_app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private UserRepository userRepository;
    private UserService userService;
    private JwtManipulator jwtManipulator;

    @PostMapping("/login")
    public ResponseEntity<TimedJwt> login(@RequestBody LoginRequest loginRequest) {
        Optional<UserDTO> loggedUser = authenticationService.login(loginRequest);

        if (loggedUser.isEmpty()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        TimedJwt jwt = jwtManipulator.generateToken(loggedUser.get());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserDTO userDTO) {

        return userService.createUser(userDTO)
                .map(user -> new ResponseEntity<HttpStatus>(HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }




}
