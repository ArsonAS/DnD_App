package com.dnd_app.service;

import com.dnd_app.dto.CharacterDTO;
import com.dnd_app.dto.ClientDTO;
import com.dnd_app.model.Salt;
import com.dnd_app.model.Client;
import com.dnd_app.repository.CharacterRepository;
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
    private final CharacterRepository characterRepository;

    public ClientService(ClientRepository clientRepository, SaltRepository saltRepository,
                         CharacterRepository characterRepository) {
        this.clientRepository = clientRepository;
        this.saltRepository = saltRepository;
        this.characterRepository = characterRepository;
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

        Optional<Client> clientOptional = clientRepository.findByUsername(clientDTO.getUsername());
        if (clientOptional.isPresent()) throw new IllegalArgumentException("Client already exists");

        Client newClient = clientDTO.fromDTO();
        hashAndSaltPassword(newClient);
        return Optional.of(new ClientDTO(clientRepository.save(newClient)));
    }

    public Optional<ClientDTO> findClientById(Long clientId){
        return Optional.of(new ClientDTO(clientRepository.findById(clientId).orElseThrow(() -> new NoSuchElementException("Client not found"))));
    }

    public Optional<CharacterDTO> createCharacter (CharacterDTO characterDTO){
        if(characterDTO == null) throw new IllegalArgumentException("Character cannot be null");
        return Optional.of(new CharacterDTO(characterRepository.save(characterDTO.fromDTO())));
    }

    public Optional<CharacterDTO> findCharacterById(Long characterId){
        return Optional.of(new CharacterDTO(characterRepository.findById(characterId).orElseThrow(() -> new NoSuchElementException("Character not found"))));
    }



}
