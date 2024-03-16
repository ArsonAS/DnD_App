package com.dnd_app.controller;

import com.dnd_app.dto.ClientDTO;
import com.dnd_app.repository.SaltRepository;
import com.dnd_app.repository.ClientRepository;
import com.dnd_app.security.jwt.JwtManipulator;
import com.dnd_app.security.jwt.TimedJwt;
import com.dnd_app.security.service.AuthenticationService;
import com.dnd_app.service.ClientService;
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
    private ClientRepository clientRepository;
    @MockBean
    private ClientService clientService;

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtManipulator jwtManipulator;

    @Test
    @WithMockUser
    public void testUserSignup_happyPath() throws Exception {
        when(clientService.createClient(any())).thenReturn(Optional.of(mock(ClientDTO.class)));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/signup")
                .with(csrf())
                .content(createJsonOfClientDTO())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }
    @Test
    @WithMockUser
    public void testClientSignup_invalidClient() throws Exception {
        when(clientService.createClient(any())).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/signup")
                .with(csrf())
                .content(createJsonOfClientDTO())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testLogin_happyPath() throws Exception {
        ClientDTO mockedClientDTO = mock(ClientDTO.class);

        when(authenticationService.login(any())).thenReturn(Optional.of(mockedClientDTO));
        when(jwtManipulator.generateToken(mockedClientDTO)).thenReturn(mock(TimedJwt.class));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/login").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"identification\":\"identification\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    public void testLogin_ClientNotFound_soUnauthorized() throws Exception {
        when(authenticationService.login(any())).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/login").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"identification\":\"identification\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isUnauthorized());
    }



    private String createJsonOfClientDTO() {
        return "{" +
                "\"username\":\"\"," +
                "\"email\":\"\"," +
                "\"password\":\"\"" +
                "}";
    }
}
