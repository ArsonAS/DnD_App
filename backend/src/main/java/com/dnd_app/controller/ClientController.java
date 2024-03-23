package com.dnd_app.controller;

import com.dnd_app.dto.CharacterDTO;
import com.dnd_app.dto.ClientDTO;
import com.dnd_app.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/characters")
    public ResponseEntity<HttpStatus> createCharacter(@RequestBody CharacterDTO characterDTO, @RequestParam Long clientId){
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


}
