package com.dnd_app.repository;

import com.dnd_app.model.Character.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
