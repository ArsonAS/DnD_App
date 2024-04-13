package com.dnd_app.repository;

import com.dnd_app.model.Campaign;
import com.dnd_app.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    Optional<Campaign> findByName(String name);
    List<Campaign> findAllByCharactersId(Long characterId);
    List<Campaign> findAllByClient(Client client);
    List<Campaign>findAllByFinished(boolean isFinished);


}
