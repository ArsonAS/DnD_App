package com.dnd_app.repository;

import com.dnd_app.model.Character.Character;
import com.dnd_app.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    List<Journal> findAllByCharacter(Character character);
}
