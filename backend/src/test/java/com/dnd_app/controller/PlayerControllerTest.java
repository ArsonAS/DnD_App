package com.dnd_app.controller;

import com.dnd_app.dto.PlayerDTO;
import com.dnd_app.repository.PlayerRepository;
import com.dnd_app.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PlayerService playerService;
    @MockBean
    private PlayerRepository playerRepository;

    @Test
    public void givenPlayerObject_whenCreatePlayer_thenReturnSavedPlayer() throws Exception{
        // given - precondition or setup
        PlayerDTO playerDTO = PlayerDTO.playerDTOBuilder()
                .username("userTest").email("email@test.com").password("password").build();

        when(playerService.createPlayer(playerDTO)).thenReturn(Optional.of(playerDTO));

        // when - action or behaviour that we are going test

        ResultActions response = mockMvc.perform(post("/api/players")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Optional.of(playerDTO)))
        );

        // then - verify the result or output using assert statements
        response.andDo(print()).andExpect(status().isOk());

}
