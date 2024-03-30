package com.dnd_app.service;

import com.dnd_app.dto.CharacterDTO;
import com.dnd_app.dto.ClientDTO;
import com.dnd_app.model.Character.Character;
import com.dnd_app.model.Character.CharacterAbilityScores;
import com.dnd_app.model.Client;
import com.dnd_app.model.Salt;
import com.dnd_app.repository.CharacterRepository;
import com.dnd_app.repository.ClientRepository;
import com.dnd_app.repository.SaltRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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
    private CharacterRepository characterRepository;
    @Mock
    private SaltRepository saltRepository;
    @InjectMocks
    private ClientService clientService;

    @Test
    public void createClientTest_HappyPath(){
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
    public void createClientTest_NullClient(){
        assertThatThrownBy(() -> clientService.createClient(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Client cannot be null");
    }

    @Test
    void createClientTest_UsernameAlreadyInUse() {
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
    public void findClientByIdTest_HappyPath() {
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
    public void findClientByIdTest_NotFoundTest() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.findClientById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Client not found");
    }

    @Test
    public void updateRoleByIdTest_HappyPath() {
        Client client = Client.clientBuilder()
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("PLAYER")
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDTO originalClient = clientService.createClient(new ClientDTO(client)).get();

        ClientDTO updatedClient = clientService.updateRoleById(1L).get();

        verify(clientRepository, times(2)).save(any(Client.class));
        verify(clientRepository, times(1)).findById(1L);
        assertThat(originalClient.getRole()).isEqualTo("PLAYER");
        assertThat(updatedClient.getRole()).isEqualTo("DM");
    }

    @Test
    public void createCharacterTest_HappyPath() {
        Client client = Client.clientBuilder()
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("PLAYER")
                .build();

        Character character = Character.characterBuilder()
                .client(client)
                .name("Wyll")
                .classe("Sorcier")
                .level(3)
                .background("Soldat")
                .race("Humaine")
                .alignment("Bien légitime")
                .experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(9, 13, 15, 14, 11, 16))
                .build();

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(characterRepository.save(any(Character.class))).thenReturn(character);

        CharacterDTO characterDTO = clientService.createCharacter(new CharacterDTO(character), anyLong()).get();

        verify(clientRepository, times(1)).findById(anyLong());
        verify(characterRepository, times(1)).save(character);
        assertThat(characterDTO.equals(new CharacterDTO(character)));

    }

    @Test
    public void findCharacterByIdTest_HappyPath() {
        // Arrange
        Client client = Client.clientBuilder()
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("PLAYER")
                .build();

        Character character = Character.characterBuilder()
                .client(client)
                .name("Wyll")
                .classe("Sorcier")
                .level(3)
                .background("Soldat")
                .race("Humaine")
                .alignment("Bien légitime")
                .experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(9, 13, 15, 14, 11, 16))
                .build();


        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));

        // Act
        CharacterDTO characterDTO = clientService.findCharacterById(1L).orElseThrow();

        // Assert
        assertThat(characterDTO.getName()).isEqualTo(character.getName());
        assertThat(characterDTO.getClasse()).isEqualTo(character.getClasse());
        assertThat(characterDTO.getRace()).isEqualTo(character.getRace());
        assertThat(characterDTO.getAlignment()).isEqualTo(character.getAlignment());
        assertThat(characterDTO.getBackground()).isEqualTo(character.getBackground());
        assertThat(characterDTO.getCharacterSkills()).isEqualTo(character.getCharacterSkills());

    }

    @Test
    public void findAllCharactersByClientIdTest_HappyPath() {
        Client client = Client.clientBuilder()
                .username("Lilian")
                .email("lilian@dnd.com")
                .password("Password1")
                .role("PLAYER")
                .build();

        Character character1 = Character.characterBuilder()
                .client(client)
                .name("Wyll")
                .classe("Sorcier")
                .level(3)
                .background("Soldat")
                .race("Humaine")
                .alignment("Bien légitime")
                .experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(9, 13, 15, 14, 11, 16))
                .build();

        Character character2 = Character.characterBuilder()
                .client(client)
                .name("Shadowheart")
                .classe("Clerc")
                .level(3)
                .background("Oursin")
                .race("Elfe")
                .alignment("Neutre")
                .experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(12, 14, 14, 10, 16, 10))
                .build();

        List<Character> characterList = new ArrayList<>();

        characterList.add(character1);
        characterList.add(character2);

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(characterRepository.findAllByClient(any(Client.class))).thenReturn(characterList);

        List<CharacterDTO> characterDTOList = clientService.findAllCharactersByClientId(anyLong());

        verify(clientRepository, times(1)).findById(anyLong());
        verify(characterRepository, times(1)).findAllByClient(client);
        assertThat(characterDTOList.size()).isEqualTo(2);
    }


}
