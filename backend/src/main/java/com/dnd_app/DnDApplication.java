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

    private ClientRepository clientRepository;
    private CharacterRepository characterRepository;
    private CampaignRepository campaignRepository;
    private ClientService clientService;
    //private DMService dmService;

    public static void main(String[] args){
        SpringApplication.run(DnDApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        Client client = clientRepository.save(
                Client.clientBuilder()
                .username("Lilian").email("lilian@dnd.com")
                .password("Password1").role("PLAYER").build());

        Character character1 = characterRepository.save(
                Character.characterBuilder()
                .id(1L).client(client).name("Astarion").classe("Voleur").level(3).background("Charlatan")
                .race("Elfe").alignment("Mal neutre").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(8, 17, 14, 13, 13, 10))
                .build());

        Character character2 = characterRepository.save(
                Character.characterBuilder()
                .id(2L).client(client).name("Gale").classe("Magicien").level(3).background("Sage")
                .race("Humaine").alignment("Bien chaotique").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(9, 14, 15, 16, 11, 13))
                .build());

        Character character3 = characterRepository.save(
                Character.characterBuilder()
                .id(3L).client(client).name("Shadowheart").classe("Clerc").level(3).background("Oursin")
                .race("Elfe").alignment("Neutre").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(12, 14, 14, 10, 16, 10))
                .build());

        Character character4 = characterRepository.save(
                Character.characterBuilder()
                .id(4L).client(client).name("Wyll").classe("Sorcier").level(3).background("Soldat")
                .race("Humaine").alignment("Bien légitime").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(9, 13, 15, 14, 11, 16))
                .build());

        Character character5 = characterRepository.save(
                Character.characterBuilder()
                .id(5L).client(client).name("Lae’zel").classe("Combattante").level(3).background("Soldat")
                .race("Grenouille").alignment("Mal légitime").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(17, 13, 14, 11, 12, 8))
                .build());

        Character character6 = characterRepository.save(
                Character.characterBuilder()
                .id(6L).client(client).name("Karlach").classe("Barbare").level(3).background("Soldat")
                .race("Tieffelin").alignment("Bien neutre").experiencePoints(1800)
                .characterAbilityScores(new CharacterAbilityScores(17, 13, 14, 11, 12, 8))
                .build());

        Campaign campaign1 = campaignRepository.save(
                Campaign.campaignBuilder()
                .id(1L).name("Premiere Campagne")
                .notes("06/4/2024 -> Rien de nouveau.")
                .client(client)
                .build());

        Campaign campaign2 = campaignRepository.save(
                Campaign.campaignBuilder()
                .id(2L).name("Deuxieme Campagne")
                .notes("07/4/2024 -> Rien de nouveau.")
                .client(client)
                .build());

        campaign1.getCharacters().add(character1);
        campaign1.getCharacters().add(character2);
        campaign1.getCharacters().add(character3);
        campaign1.getCharacters().add(character4);
        campaign1.getCharacters().add(character5);
        campaignRepository.save(campaign1);

    }


}
