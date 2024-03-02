package com.dnd_app.service;

import com.dnd_app.dto.PlayerDTO;
import com.dnd_app.model.Player;
import com.dnd_app.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<PlayerDTO> createPlayer(PlayerDTO playerDTO){
        if (playerDTO == null) throw new IllegalArgumentException("Player cannot be null");

        Optional<Player> playerOptional = playerRepository.findByUsername(playerDTO.getUsername());

        if (playerOptional.isPresent()) throw new IllegalArgumentException("User already exists");

        return Optional.of(new PlayerDTO(playerRepository.save(playerDTO.fromDTO())));
    }
}
