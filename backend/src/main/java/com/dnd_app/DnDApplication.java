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
                .id(1L).name("L’immense Arachnatron. ")
                .notes("06/4/2024 -> We're in a village of a about 7 dozen people. We're surrounded by undead humanoids, monstrous humanoids, and animals. They're mooks, we could wipe them out with some patience and a good battle strategy, but there's a bigger problem on the horizon. Our scrying and divinations have let us know that we've got about one week, give or take a day, to get ready for a wave of kythons to attack the town in an orgy of blood and fury.\n" +
                        "\n" +
                        "There's a LOT of them. A LOT of juveniles, a good number of broodlings and adults, and a single slaymaster (you have to know about the kythons for that to make sense). Worse, anything we kill will get up and fight us again, though probably weaker. WORSE WORSE, anything THEY kill will get up and fight us.\n" +
                        "\n" +
                        "I think our DM wanted us to quest for something to help us protect the town, but we threw a curveball at him, and suggested:\n" +
                        "\n" +
                        "Us: \"What if we taught the village how to fight?\"\n" +
                        "DM: \"In a week?\"\n" +
                        "Us: \"Better than nothing.\"\n" +
                        "\n" +
                        "In our games, 6th level means you're a downright famous member of your chosen class. Maybe not a world-shatterer, but a 6th level cleric is almost definitely someone other clerics have heard of. A 6th level wizard is a respectable and admired arcanist. Ect.\n" +
                        "\n" +
                        "Since we're considered \"downright incredible\" by 99% of the worlds population (99% of the worlds population being 1rst level NPC classes), and since our DM liked our suggestion so much, he's letting us go for it. We get to transform the NPCs into ready adventurers. The solid week of hands-on training will give them a (one-time only) jump to second level, and they loose their NPC classes, gaining real base classes instead.")
                .client(client)
                .build();
        Optional<CampaignDTO> optionalCampaignDTO1 = clientService.createCampaign(new CampaignDTO(campaign1), client.getId());
        if(!optionalCampaignDTO1.isPresent()) return;

        Campaign campaign2 = Campaign.campaignBuilder()
                .id(2L).name("La conspiration du Grand Conseil")
                .notes("07/4/2024 -> Rien de nouveau.")
                .client(client)
                .build();
        campaign2.setFinished(true);
        Optional<CampaignDTO> optionalCampaignDTO2 = clientService.createCampaign(new CampaignDTO(campaign2), client.getId());
        if(!optionalCampaignDTO2.isPresent()) return;


        clientService.addCharacterToCampaign(character1.getId(), campaign1.getId());


    }


}
