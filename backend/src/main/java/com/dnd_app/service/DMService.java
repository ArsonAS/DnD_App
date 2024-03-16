package com.dnd_app.service;

import com.dnd_app.dto.CampaignDTO;
import com.dnd_app.model.Campaign;
import com.dnd_app.model.Client;
import com.dnd_app.repository.CampaignRepository;
import com.dnd_app.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DMService {

    private CampaignRepository campaignRepository;
    private ClientRepository clientRepository;

    public DMService(CampaignRepository campaignRepository, ClientRepository clientRepository) {
        this.campaignRepository = campaignRepository;
        this.clientRepository = clientRepository;
    }

    public Optional<CampaignDTO> createCampaign(CampaignDTO campaignDTO){
        if (campaignDTO == null) throw new IllegalArgumentException("Campaign cannot be null");
        Client client = clientRepository.findById(campaignDTO.getClientId()).orElseThrow();

        Campaign campaign = campaignDTO.fromDTO();
        return Optional.of(new CampaignDTO(campaignRepository.save(campaignDTO.fromDTO())));
    }

    public Optional<CampaignDTO> findCampaignById(Long campaignId){
        return Optional.of(new CampaignDTO(campaignRepository.findById(campaignId).orElseThrow(() -> new NoSuchElementException("Campaign not found"))));
    }

}
