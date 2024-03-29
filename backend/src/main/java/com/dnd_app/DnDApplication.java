package com.dnd_app;

import com.dnd_app.dto.CharacterDTO;
import com.dnd_app.dto.ClientDTO;
import com.dnd_app.model.Character.CharacterAbilityScores;
import com.dnd_app.repository.ClientRepository;
import com.dnd_app.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class DnDApplication implements CommandLineRunner {

    private ClientRepository clientRepository;
    private ClientService clientService;

    public static void main(String[] args){
        SpringApplication.run(DnDApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        clientService.createClient(ClientDTO.clientDTOBuilder()
                        .username("Lilian")
                        .email("lilian@dnd.com")
                        .password("Password1")
                        .role("PLAYER")
                .build());

        clientService.createCharacter(CharacterDTO.characterDTOBuilder()
                        .clientId(1L)
                        .name("Astarion")
                        .classe("Voleur")
                        .level(3)
                        .background("Charlatan")
                        .race("Elfe")
                        .alignment("Mal neutre")
                        .experiencePoints(1800)
                        .characterAbilityScores(new CharacterAbilityScores(8, 17, 14, 13, 13, 10))
                .build(), 1L);

        clientService.createCharacter(CharacterDTO.characterDTOBuilder()
                .clientId(2L)
                .name("Gale")
                .classe("Magicien")
                .level(3)
                .background("Sage")
                .race("Humaine")
                .alignment("Bien chaotique")
                .experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(9, 14, 15, 16, 11, 13))
                .build(), 1L);

        clientService.createCharacter(CharacterDTO.characterDTOBuilder()
                .clientId(3L)
                .name("Shadowheart")
                .classe("Clerc")
                .level(3)
                .background("Oursin")
                .race("Elfe")
                .alignment("Neutre")
                .experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(12, 14, 14, 10, 16, 10))
                .build(), 1L);

        clientService.createCharacter(CharacterDTO.characterDTOBuilder()
                .clientId(4L)
                .name("Wyll")
                .classe("Sorcier")
                .level(3)
                .background("Soldat")
                .race("Humaine")
                .alignment("Bien légitime")
                .experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(9, 13, 15, 14, 11, 16))
                .build(), 1L);

        clientService.createCharacter(CharacterDTO.characterDTOBuilder()
                .clientId(5L)
                .name("Lae’zel")
                .classe("Combattante")
                .level(3)
                .background("Soldat")
                .race("Grenouille")
                .alignment("Mal légitime")
                .experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(17, 13, 14, 11, 12, 8))
                .build(), 1L);

        clientService.createCharacter(CharacterDTO.characterDTOBuilder()
                .clientId(6L)
                .name("Karlach")
                .classe("Barbare")
                .level(3)
                .background("Soldat")
                .race("Tieffelin")
                .alignment("Bien neutre")
                .experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(17, 13, 14, 11, 12, 8))
                .build(), 1L);
    }
}
