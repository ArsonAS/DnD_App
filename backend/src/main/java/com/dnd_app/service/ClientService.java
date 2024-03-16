package com.dnd_app.service;

import com.dnd_app.dto.ClientDTO;
import com.dnd_app.model.Salt;
import com.dnd_app.model.Client;
import com.dnd_app.repository.SaltRepository;
import com.dnd_app.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final SaltRepository saltRepository;

    public ClientService(ClientRepository clientRepository, SaltRepository saltRepository) {
        this.clientRepository = clientRepository;
        this.saltRepository = saltRepository;
    }


    protected void hashAndSaltPassword(Client client) {
        String generatedSalt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(client.getPassword(), generatedSalt);

        client.setPassword(hashedPassword);
        saltRepository.save(new Salt(null, client, generatedSalt));
    }

    @Transactional
    public Optional<ClientDTO> createClient(ClientDTO clientDTO){
        if (clientDTO == null) throw new IllegalArgumentException("Client cannot be null");

        Optional<Client> userOptional = clientRepository.findByUsername(clientDTO.getUsername());
        if (userOptional.isPresent()) throw new IllegalArgumentException("Client already exists");

        Client newClient = clientDTO.fromDTO();
        hashAndSaltPassword(newClient);
        return Optional.of(new ClientDTO(clientRepository.save(newClient)));
    }

    public Optional<ClientDTO> findClientById(Long clientId){
        return Optional.of(new ClientDTO(clientRepository.findById(clientId).orElseThrow(() -> new NoSuchElementException("Client not found"))));
    }

}
