package com.dnd_app.controller;

import com.dnd_app.dto.CampaignDTO;
import com.dnd_app.dto.CharacterDTO;
import com.dnd_app.dto.ClientDTO;
import com.dnd_app.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id){
        return clientService.findClientById(id)
                .map(client -> ResponseEntity.ok(client))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClientById(@RequestParam("clientId") Long clientId){
        return clientService.updateRoleById(clientId)
                .map(client -> ResponseEntity.ok(client))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/characters")
    public ResponseEntity<HttpStatus> createCharacter(@RequestBody CharacterDTO characterDTO, @RequestParam("clientId") Long clientId){
        return clientService.createCharacter(characterDTO, clientId)
                .map(character -> new ResponseEntity<HttpStatus>(HttpStatus.CREATED))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/character")
    public ResponseEntity<CharacterDTO> getCharacterById(@RequestParam("charId") Long charId){
        return clientService.findCharacterById(charId)
                .map(character -> ResponseEntity.ok(character))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/characters")
    public ResponseEntity<List<CharacterDTO>> getAllCharactersByClientId(@RequestParam("clientId") Long clientId){
        return ResponseEntity.ok(clientService.findAllCharactersByClientId(clientId));
    }


    @PostMapping("/campaigns")
    public ResponseEntity<HttpStatus> createCampaign(@RequestBody CampaignDTO campaignDTO, @RequestParam("clientId") Long clientId){
        return clientService.createCampaign(campaignDTO, clientId)
                .map(campaign -> new ResponseEntity<HttpStatus>(HttpStatus.CREATED))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/campaign")
    public ResponseEntity<CampaignDTO> getCampaignById(@RequestParam("campaignId") Long campaignId){
        return clientService.findCampaignById(campaignId)
                .map(campaign -> ResponseEntity.ok(campaign))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/campaigns")
    public ResponseEntity<List<CampaignDTO>> getAllCampaignsByClientId(@RequestParam("clientId") Long clientId){
        return ResponseEntity.ok(clientService.findAllCampaignsByClientId(clientId));
    }






}
