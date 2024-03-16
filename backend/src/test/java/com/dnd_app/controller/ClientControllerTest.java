package com.dnd_app.controller;

import com.dnd_app.dto.ClientDTO;
import com.dnd_app.repository.ClientRepository;
import com.dnd_app.repository.SaltRepository;
import com.dnd_app.security.jwt.JwtManipulator;
import com.dnd_app.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(com.dnd_app.controller.ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtManipulator jwtManipulator;
    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private ClientService clientService;


    @Test
    @WithMockUser
    public void getClientById_happyPath_test() throws Exception {
        ClientDTO clientDTO = ClientDTO.clientDTOBuilder()
                .id(1L)
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("PLAYER")
                .build();

        when(clientService.findClientById(1L)).thenReturn(Optional.of(clientDTO));

        mockMvc.perform(get("/api/clients/{id}", 1L).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("Lilian"))
                .andExpect(jsonPath("$.email").value("lilian@dnd.com"))
                .andExpect(jsonPath("$.role").value("PLAYER"))
        ;
    }

    @Test
    @WithMockUser
    public void getClientById_notFound_test() throws Exception{
        when(clientService.findClientById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clients/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    private ClientDTO createClientDTO(){
        return ClientDTO.clientDTOBuilder()
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("PLAYER")
                .build();
    }
}
