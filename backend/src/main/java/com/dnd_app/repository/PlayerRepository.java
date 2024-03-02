package com.dnd_app.repository;

import com.dnd_app.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByEmail(String email);

    Optional<Player> findByUsername(String username);
}