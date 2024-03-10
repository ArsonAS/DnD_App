package com.dnd_app.security.service;

import com.dnd_app.dto.ClientDTO;
import com.dnd_app.model.Client;
import com.dnd_app.model.Salt;
import com.dnd_app.repository.SaltRepository;
import com.dnd_app.repository.ClientRepository;
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
    private final ClientRepository clientRepository;
    private final SaltRepository saltRepository;

    public Optional<ClientDTO> login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        Optional<Client> optionalUser = clientRepository.findByUsername(username);

        Client client = optionalUser.orElseThrow();
        Salt salt = saltRepository.findByClient(client).orElseThrow();

        String pw = loginRequest.getPassword();
        String hashedPw = BCrypt.hashpw(pw, salt.getValue());

        if (!hashedPw.equals(client.getPassword()))
            return Optional.empty();

        return Optional.of(new ClientDTO(client));
    }
}
