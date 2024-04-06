package com.dnd_app.dto;

import com.dnd_app.model.Character.Character;
import com.dnd_app.model.Journal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class JournalDTO {
    private Long id;
    private String entry;
    private LocalDate entryDate;
    private Long characterId;

    public JournalDTO(Journal journal) {
        this.id = journal.getId();
        this.entry = journal.getEntry();
        this.entryDate = journal.getEntryDate();
        this.characterId = journal.getCharacter().getId();
    }

    @Builder(builderMethodName = "journalDTOBuilder")
    public JournalDTO(Long id, String entry, LocalDate entryDate, Long characterId) {
        this.id = id;
        this.entry = entry;
        this.entryDate = entryDate;
        this.characterId = characterId;
    }

    public Journal fromDTO(){
        return Journal.journalBuilder()
                .id(id)
                .entry(entry)
                .entryDate(entryDate)
                .build();
    }

}
