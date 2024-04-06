package com.dnd_app.dto;

import com.dnd_app.model.Journal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PartyDTO {
    private Long id;
    private Long character1Id;

    private Long character2Id;

    private Long character3Id;

    private Long character4Id;

    private Long character5Id;

    private Long campaignId;

    public PartyDTO(Journal journal) {
        this.id = journal.getId();
        this.character1Id = journal.getCharacter1().getId();
        this.character2Id = journal.getCharacter2().getId();
        this.character3Id = journal.getCharacter3().getId();
        this.character4Id = journal.getCharacter4().getId();
        this.character5Id = journal.getCharacter5().getId();
    }

    @Builder(builderMethodName = "partyDTOBuilder")
    public PartyDTO(Long id, Long character1Id, Long character2Id, Long character3Id, Long character4Id, Long character5Id) {
        this.id = id;
        this.character1Id = character1Id;
        this.character2Id = character2Id;
        this.character3Id = character3Id;
        this.character4Id = character4Id;
        this.character5Id = character5Id;
    }
}
