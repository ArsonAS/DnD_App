package com.dnd_app.controller;

import com.dnd_app.dto.CharacterDTO;
import com.dnd_app.dto.ClientDTO;
import com.dnd_app.model.Character.CharacterAbilityScores;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    public void givenClientId_whenGetClientById_thenReturnClientDTOObject() throws Exception {
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
    public void givenClientId_whenGetClientById_thenReturn_IsNotFound() throws Exception{
        when(clientService.findClientById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clients/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void givenUpdatedClient_whenUpdateClientRole_thenReturnUpdatedClientObject() throws Exception{
        // given - precondition or setup
        ClientDTO clientSaved = ClientDTO.clientDTOBuilder()
                .id(1L)
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("PLAYER")
                .build();

        ClientDTO clientUpdated = ClientDTO.clientDTOBuilder()
                .id(1L)
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("DM")
                .build();

        given(clientRepository.findById(clientSaved.getId())).willReturn(Optional.of(clientSaved.fromDTO()));
        given(clientService.updateRoleById(anyLong())).willReturn(Optional.of(clientUpdated));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/clients/{id}", 1L)
                .with(csrf())
                .param("clientId", "1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientUpdated)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(clientUpdated.getId().intValue())))
                .andExpect(jsonPath("$.username", is(clientUpdated.getUsername())))
                .andExpect(jsonPath("$.email", is(clientUpdated.getEmail())))
                .andExpect(jsonPath("$.password", is(clientUpdated.getPassword())))
                .andExpect(jsonPath("$.role", is(clientUpdated.getRole())));
    }

    @Test
    @WithMockUser
    public void givenUpdatedClient_whenUpdateClientRole_thenReturnIsNotFound() throws Exception {

        when(clientService.updateRoleById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/clients/{id}", 1L)
                        .with(csrf())
                        .param("clientId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void givenCharacterObject_whenCreateCharacter_thenReturnIsCreated() throws Exception{
        // given - precondition or setup
        ClientDTO clientDTO = ClientDTO.clientDTOBuilder()
                    .id(1L)
                    .username("Lilian")
                    .email("lilian@dnd.com")
                    .password("Password1")
                    .role("PLAYER")
                .build();

        CharacterDTO characterDTO = CharacterDTO.characterDTOBuilder()
                    .id(1L)
                    .clientId(6L)
                    .name("Karlach")
                    .classe("Barbare")
                    .level(3)
                    .background("Soldat")
                    .race("Tieffelin")
                    .alignment("Bien neutre")
                    .experiencePoints(1800)
                    .characterAbilityScores(new CharacterAbilityScores(17, 13, 14, 11, 12, 8))
                .build();

        when(clientService.findClientById(1L)).thenReturn(Optional.of(clientDTO));
        when(clientService.createCharacter(any(CharacterDTO.class), anyLong())).thenReturn(Optional.of(characterDTO));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/clients/characters")
                .with(csrf())
                .param("clientId", "1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Optional.of(characterDTO))));

        // then - verify the result or output using assert statements
        response.andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void givenCharacterId_whenGetCharacterById_thenReturnCharacterDTOObject() throws Exception {
        CharacterDTO characterDTO = CharacterDTO.characterDTOBuilder()
                    .id(1L)
                    .clientId(1L)
                    .name("Karlach")
                    .classe("Barbare")
                    .level(3)
                    .background("Soldat")
                    .race("Tieffelin")
                    .alignment("Bien neutre")
                    .experiencePoints(1800)
                    .characterAbilityScores(new CharacterAbilityScores(17, 13, 14, 11, 12, 8))
                .build();

        when(clientService.findCharacterById(1L)).thenReturn(Optional.of(characterDTO));

        mockMvc.perform(get("/api/clients/character", 1L)
                        .with(csrf())
                        .param("charId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clientId").value(1L))
                .andExpect(jsonPath("$.name").value("Karlach"))
                .andExpect(jsonPath("$.classe").value("Barbare"))
                .andExpect(jsonPath("$.level").value(3))
                .andExpect(jsonPath("$.background").value("Soldat"))
                .andExpect(jsonPath("$.race").value("Tieffelin"))
                .andExpect(jsonPath("$.experiencePoints").value(1800))

        ;
    }

    @Test
    @WithMockUser
    public void givenClientId_whenGetAllCharactersByClientId_thenReturnCharacterDTOList() throws Exception {
        // given - precondition or setup
        ClientDTO clientDTO = ClientDTO.clientDTOBuilder()
                    .id(1L)
                    .username("Lilian")
                    .email("lilian@dnd.com")
                    .password("Password1")
                    .role("PLAYER")
                .build();
        CharacterDTO characterDTO1 = CharacterDTO.characterDTOBuilder()
                    .id(4L)
                    .clientId(1L)
                    .name("Wyll")
                    .classe("Sorcier")
                    .level(3)
                    .background("Soldat")
                    .race("Humaine")
                    .alignment("Bien légitime")
                    .experiencePoints(1800)
                    .characterAbilityScores(new CharacterAbilityScores(9, 13, 15, 14, 11, 16))
                .build();

        CharacterDTO characterDTO2 = CharacterDTO.characterDTOBuilder()
                    .id(5L)
                    .clientId(1L)
                    .name("Lae’zel")
                    .classe("Combattante")
                    .level(3)
                    .background("Soldat")
                    .race("Grenouille")
                    .alignment("Mal légitime")
                    .experiencePoints(1800)
                    .characterAbilityScores(new CharacterAbilityScores(17, 13, 14, 11, 12, 8))
                .build();

        List<CharacterDTO> characterDTOList = new ArrayList<>();
        characterDTOList.add(characterDTO1);
        characterDTOList.add(characterDTO2);

        when(clientService.findClientById(1L)).thenReturn(Optional.of(clientDTO));
        given(clientService.findAllCharactersByClientId(clientDTO.getId())).willReturn(characterDTOList);

        // when -  action or the behaviour that we are going test
        mockMvc.perform(get("/api/clients/characters")
                .param("clientId", "1"))
        // then - verify the output
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(characterDTOList.size())));
    }


}
