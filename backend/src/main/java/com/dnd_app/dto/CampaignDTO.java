package com.dnd_app.dto;

import com.dnd_app.model.Campaign;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CampaignDTO {
    private Long id;
    private String name;
    private String notes;
    private boolean finished;
    private Long clientId;
    //private Long partiId;

    public CampaignDTO(Campaign campaign) {
        this.id = campaign.getId();
        this.name = campaign.getName();
        this.notes = campaign.getNotes();
        this.finished = campaign.isFinished();
        this.clientId = campaign.getClient().getId();
        //this.partiId = campaign.getParty().getId();
    }

    @Builder(builderMethodName = "campaignDTOBuilder")
    public CampaignDTO(Long id, String name, String notes, boolean finished, Long clientId, Long partiId) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.finished = finished;
        this.clientId = clientId;
        //this.partiId = partiId;
    }

    public Campaign fromDTO(){
        return Campaign.campaignBuilder()
                .id(id)
                .name(name)
                .notes(notes)
                .build();
    }
}
