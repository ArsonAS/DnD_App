package com.dnd_app.repository;

import com.dnd_app.model.Salt;
import com.dnd_app.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaltRepository extends JpaRepository<Salt, Long> {
    Optional<Salt> findByClient(Client client);
}
