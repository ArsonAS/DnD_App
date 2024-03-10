package com.dnd_app.service;

import com.dnd_app.dto.ClientDTO;
import com.dnd_app.model.Client;
import com.dnd_app.model.Salt;
import com.dnd_app.repository.ClientRepository;
import com.dnd_app.repository.SaltRepository;
import com.dnd_app.security.payload.request.LoginRequest;
import com.dnd_app.security.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private SaltRepository saltRepository;
    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testLogin_happyPath() {
        LoginRequest loginRequest = new LoginRequest("email@email.com", "password");
        Client mockedClient = mock(Client.class);
        Salt salt = createNewSalt(mockedClient);

        String hashedPw = BCrypt.hashpw("password", salt.getValue());

        when(clientRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(mockedClient));
        when(saltRepository.findByClient(mockedClient)).thenReturn(Optional.of(salt));
        when(mockedClient.getPassword()).thenReturn(hashedPw);


        Optional<ClientDTO> clientDTO = authenticationService.login(loginRequest);

        assertThat(clientDTO).isPresent();
    }

    @Test
    public void testLogin_invalidUsername() {
        LoginRequest loginRequest = new LoginRequest("email@email.com", "password");

        when(clientRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationService.login(loginRequest)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testLogin_invalidPassword() {
        LoginRequest loginRequest = new LoginRequest("email@email.com", "password");
        Client mockedClient = mock(Client.class);

        Salt salt = createNewSalt(mockedClient);

        when(clientRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(mockedClient));
        when(saltRepository.findByClient(mockedClient)).thenReturn(Optional.of(salt));
        when(mockedClient.getPassword()).thenReturn("anIncorrectPassword");

        assertThat(authenticationService.login(loginRequest)).isEmpty();
    }

    private Salt createNewSalt(Client client) {
        String generatedSalt = BCrypt.gensalt();
        return new Salt(null, client, generatedSalt);
    }
}
