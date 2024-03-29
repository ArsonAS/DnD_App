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
                        .name("Xefoul Snaromers")
                        .classe("Wizard")
                        .level(3)
                        .background("Mercenary Veteran")
                        .race("Dark Elf")
                        .alignment("Neutral")
                        .experiencePoints(1800)
                        .characterAbilityScores(new CharacterAbilityScores(10, 10, 10, 10, 10, 10))
                .build(), 1L);
    }
}
