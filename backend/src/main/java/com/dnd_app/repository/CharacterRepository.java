package com.dnd_app.repository;

import com.dnd_app.model.Character.Character;
import com.dnd_app.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findAllByClient(Client client);
}
