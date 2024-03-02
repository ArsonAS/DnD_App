package com.dnd_app.controller;

import com.dnd_app.dto.PlayerDTO;
import com.dnd_app.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/players")
public class PlayerController {
    private PlayerService playerService;


    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody PlayerDTO playerDTO){
        return playerService.createPlayer(playerDTO)
                .map(player -> ResponseEntity.ok(playerDTO))
                .orElse(ResponseEntity.notFound().build());
    }


}
