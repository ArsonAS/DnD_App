package com.dnd_app;

import com.dnd_app.dto.CampaignDTO;
import com.dnd_app.dto.CharacterDTO;
import com.dnd_app.dto.ClientDTO;
import com.dnd_app.model.Campaign;
import com.dnd_app.model.Character.Character;
import com.dnd_app.model.Character.CharacterAbilityScores;
import com.dnd_app.model.Client;
import com.dnd_app.repository.CampaignRepository;
import com.dnd_app.repository.CharacterRepository;
import com.dnd_app.repository.ClientRepository;
import com.dnd_app.service.ClientService;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
@AllArgsConstructor
public class DnDApplication implements CommandLineRunner {

    private ClientService clientService;


    public static void main(String[] args){
        SpringApplication.run(DnDApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        Client client = Client.clientBuilder()
                .username("Lilian").email("lilian@dnd.com")
                .password("Password1").role("PLAYER")
                .build();
        Optional<ClientDTO> optionalClientDTO = clientService.createClient(new ClientDTO(client));
        if(!optionalClientDTO.isPresent()) return;
        ClientDTO clientDTO = optionalClientDTO.get();
        client = clientDTO.fromDTO();


        Character character1 = Character.characterBuilder()
                .id(1L).client(client).name("Astarion").classe("Voleur").level(3).background("Charlatan")
                .race("Elfe").alignment("Mal neutre").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(8, 17, 14, 13, 13, 10))
                .build();
        Optional<CharacterDTO> characterOptional1 = clientService.createCharacter(new CharacterDTO(character1), client.getId());
        if(!characterOptional1.isPresent()) return;
        CharacterDTO characterDTO = characterOptional1.orElseThrow();

        Character character2 = Character.characterBuilder()
                .id(2L).client(client).name("Gale").classe("Magicien").level(3).background("Sage")
                .race("Humaine").alignment("Bien chaotique").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(9, 14, 15, 16, 11, 13))
                .build();
        Optional<CharacterDTO> characterOptional2 = clientService.createCharacter(new CharacterDTO(character2), client.getId());
        if(!characterOptional2.isPresent()) return;

        Character character3 = Character.characterBuilder()
                .id(3L).client(client).name("Shadowheart").classe("Clerc").level(3).background("Oursin")
                .race("Elfe").alignment("Neutre").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(12, 14, 14, 10, 16, 10))
                .build();
        Optional<CharacterDTO> characterOptional3 = clientService.createCharacter(new CharacterDTO(character3), client.getId());
        if(!characterOptional3.isPresent()) return;

        Character character4 = Character.characterBuilder()
                .id(4L).client(client).name("Wyll").classe("Sorcier").level(3).background("Soldat")
                .race("Humaine").alignment("Bien légitime").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(9, 13, 15, 14, 11, 16))
                .build();
        Optional<CharacterDTO> characterOptional4 = clientService.createCharacter(new CharacterDTO(character4), client.getId());
        if(!characterOptional4.isPresent()) return;

        Character character5 = Character.characterBuilder()
                .id(5L).client(client).name("Lae’zel").classe("Combattante").level(3).background("Soldat")
                .race("Grenouille").alignment("Mal légitime").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(17, 13, 14, 11, 12, 8))
                .build();
        Optional<CharacterDTO> characterOptional5 = clientService.createCharacter(new CharacterDTO(character5), client.getId());
        if(!characterOptional5.isPresent()) return;

        Character character6 = Character.characterBuilder()
                .id(6L).client(client).name("Karlach").classe("Barbare").level(3).background("Soldat")
                .race("Tieffelin").alignment("Bien neutre").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(17, 13, 14, 11, 12, 8))
                .build();
        Optional<CharacterDTO> characterOptional6 = clientService.createCharacter(new CharacterDTO(character6), client.getId());
        if(!characterOptional6.isPresent()) return;

        Campaign campaign1 = Campaign.campaignBuilder()
                .id(1L).name("Premiere Campagne")
                .notes("06/4/2024 -> Rien de nouveau.")
                .client(client)
                .build();
        Optional<CampaignDTO> optionalCampaignDTO1 = clientService.createCampaign(new CampaignDTO(campaign1), client.getId());
        if(!optionalCampaignDTO1.isPresent()) return;

        Campaign campaign2 = Campaign.campaignBuilder()
                .id(2L).name("Deuxieme Campagne")
                .notes("07/4/2024 -> Rien de nouveau.")
                .client(client)
                .build();
        campaign2.setFinished(true);
        Optional<CampaignDTO> optionalCampaignDTO2 = clientService.createCampaign(new CampaignDTO(campaign2), client.getId());
        if(!optionalCampaignDTO2.isPresent()) return;


        clientService.addCharacterToCampaign(character1.getId(), campaign1.getId());


    }


}
