package com.dnd_app.controller;

import com.dnd_app.dto.UserDTO;
import com.dnd_app.repository.SaltRepository;
import com.dnd_app.repository.UserRepository;
import com.dnd_app.security.jwt.JwtManipulator;
import com.dnd_app.security.jwt.TimedJwt;
import com.dnd_app.security.service.AuthenticationService;
import com.dnd_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtManipulator jwtManipulator;

    @Test
    @WithMockUser
    public void testUserSignup_happyPath() throws Exception {
        when(userService.createUser(any())).thenReturn(Optional.of(mock(UserDTO.class)));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/signup")
                .with(csrf())
                .content(createJsonOfUserDTO())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }
    @Test
    @WithMockUser
    public void testUserSignup_invalidEmployer() throws Exception {
        when(userService.createUser(any())).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/signup")
                .with(csrf())
                .content(createJsonOfUserDTO())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testLogin_happyPath() throws Exception {
        UserDTO mockedUserDTO = mock(UserDTO.class);

        when(authenticationService.login(any())).thenReturn(Optional.of(mockedUserDTO));
        when(jwtManipulator.generateToken(mockedUserDTO)).thenReturn(mock(TimedJwt.class));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/login").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"identification\":\"identification\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    public void testLogin_UserNotFound_soUnauthorized() throws Exception {
        when(authenticationService.login(any())).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/login").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"identification\":\"identification\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isUnauthorized());
    }



    private String createJsonOfUserDTO() {
        return "{" +
                "\"username\":\"\"," +
                "\"email\":\"\"," +
                "\"password\":\"\"" +
                "}";
    }
}
