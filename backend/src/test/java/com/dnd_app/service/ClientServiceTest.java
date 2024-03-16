package com.dnd_app.service;

import com.dnd_app.dto.ClientDTO;
import com.dnd_app.model.Client;
import com.dnd_app.model.Salt;
import com.dnd_app.repository.ClientRepository;
import com.dnd_app.repository.SaltRepository;
import com.dnd_app.security.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private SaltRepository saltRepository;
    @InjectMocks
    private ClientService clientService;

    @Test
    public void createClient_HappyPath(){
        // Arrange
        ClientDTO clientDTO = ClientDTO.clientDTOBuilder()
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("PLAYER")
                .build();

        when(clientRepository.save(any(Client.class))).thenReturn(clientDTO.fromDTO());
        when(saltRepository.save(any())).thenReturn(mock(Salt.class));

        // Act
        ClientDTO dto = clientService.createClient(clientDTO).get();

        // Assert
        assertThat(dto.equals(clientDTO));
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    public void createClient_NullClient(){
        assertThatThrownBy(() -> clientService.createClient(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Client cannot be null");
    }

    @Test
    void createClient_UsernameAlreadyInUse() {
        // Arrange
        ClientDTO clientDTO = ClientDTO.clientDTOBuilder()
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("PLAYER")
                .build();
        when(clientRepository.findByUsername(anyString())).thenReturn(Optional.of(clientDTO.fromDTO()));

        assertThatThrownBy(() -> clientService.createClient(clientDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Client already exists");
    }

    @Test
    public void findClientById_happyPathTest() {
        // Arrange
        ClientDTO clientDTO = ClientDTO.clientDTOBuilder()
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("PLAYER")
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientDTO.fromDTO()));

        // Act
        ClientDTO dto = clientService.findClientById(1L).orElseThrow();

        // Assert
        assertThat(dto.getUsername()).isEqualTo("Lilian");
        assertThat(dto.getEmail()).isEqualTo("lilian@dnd.com");
    }

    @Test
    public void findClientById_notFoundTest() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.findClientById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Client not found");
    }

}
