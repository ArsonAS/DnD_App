package com.dnd_app.service;

import com.dnd_app.dto.CampaignDTO;
import com.dnd_app.dto.CharacterDTO;
import com.dnd_app.dto.ClientDTO;
import com.dnd_app.dto.JournalDTO;
import com.dnd_app.model.Campaign;
import com.dnd_app.model.Character.Character;
import com.dnd_app.model.Journal;
import com.dnd_app.model.Salt;
import com.dnd_app.model.Client;
import com.dnd_app.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final SaltRepository saltRepository;
    private final CharacterRepository characterRepository;
    private CampaignRepository campaignRepository;
    private JournalRepository journalRepository;

    public ClientService(ClientRepository clientRepository, SaltRepository saltRepository,
                         CharacterRepository characterRepository, CampaignRepository campaignRepository,
                         JournalRepository journalRepository) {
        this.clientRepository = clientRepository;
        this.saltRepository = saltRepository;
        this.characterRepository = characterRepository;
        this.campaignRepository = campaignRepository;
        this.journalRepository = journalRepository;
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
    public Optional<ClientDTO> updateRoleById(Long clientId){
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NoSuchElementException("Client not found"));

        if (client.getRole().equals("PLAYER")){
            client.setRole("DM");
        }
        else client.setRole("PLAYER");

        return Optional.of(new ClientDTO(clientRepository.save(client)));
    }

    @Transactional
    public Optional<CharacterDTO> createCharacter (CharacterDTO characterDTO, Long clientId){
        if(characterDTO == null) throw new IllegalArgumentException("Character cannot be null");

        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NoSuchElementException("Client not found"));
        Character newCharacter = characterDTO.fromDTO();
        newCharacter.setClient(client);

        return Optional.of(new CharacterDTO(characterRepository.save(newCharacter)));
    }
    public Optional<CharacterDTO> findCharacterById(Long characterId){
        return Optional.of(new CharacterDTO(characterRepository.findById(characterId).orElseThrow(() -> new NoSuchElementException("Character not found"))));
    }
    public List<CharacterDTO> findAllCharactersByClientId(Long clientId){
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NoSuchElementException("Client not found"));

        return characterRepository.findAllByClient(client).stream().map(CharacterDTO::new).toList();
    }


    @Transactional
    public Optional<CampaignDTO> createCampaign(CampaignDTO campaignDTO, Long clientId){
        if (campaignDTO == null) throw new IllegalArgumentException("Campaign cannot be null");

        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NoSuchElementException("Client not found"));
        Campaign newCampaign = campaignDTO.fromDTO();
        newCampaign.setClient(client);

        return Optional.of(new CampaignDTO(campaignRepository.save(newCampaign)));
    }
    public Optional<CampaignDTO> findCampaignById(Long campaignId){
        return Optional.of(new CampaignDTO(campaignRepository.findById(campaignId).orElseThrow(() -> new NoSuchElementException("Campaign not found"))));
    }
    public List<CampaignDTO> findAllCampaignsByClientId(Long clientId){
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NoSuchElementException("Client not found"));

        return campaignRepository.findAllByClient(client).stream().map(CampaignDTO::new).toList();
    }

    public Optional<CampaignDTO> addCharacterToCampaign(Long characterId, Long campaignId){
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new NoSuchElementException("Character not found"));
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(() -> new NoSuchElementException("Campaign not found"));

        campaign.getCharacters().add(character);
        return Optional.of(new CampaignDTO(campaignRepository.save(campaign)));
    }

    public List<CharacterDTO> findAllCharactersByCampaignId(Long campaignId){
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(() -> new NoSuchElementException("Campaign not found"));
        return characterRepository.findAllByCampaignsId(campaign.getId()).stream().map(CharacterDTO::new).toList();
    }

    public List<CampaignDTO> findAllCampaignsByCharacterId(Long characterId){
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new NoSuchElementException("Character not found"));
        return campaignRepository.findAllByCharactersId(character.getId()).stream().map(CampaignDTO::new).toList();
    }

    @Transactional
    public Optional<JournalDTO> createJournalEntry(JournalDTO journalDTO){
        if (journalDTO == null) throw new IllegalArgumentException("Journal cannot be null");

        Character character = characterRepository.findById(journalDTO.getCharacterId()).orElseThrow(() -> new NoSuchElementException("Character not found"));
        Journal newJournalEntry = journalDTO.fromDTO();
        newJournalEntry.setCharacter(character);

        return Optional.of(new JournalDTO(journalRepository.save(newJournalEntry)));
    }







}
