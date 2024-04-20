package com.dnd_app.model;

import com.dnd_app.model.Character.Character;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Journal {
    @Id
    @SequenceGenerator(name = "parti_gen", sequenceName = "parti_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parti_gen")
    private Long id;
    @Column(length = 50000)
    private String entry;
    private LocalDate entryDate;

    @ManyToOne
    @JoinColumn(name = "character_id")
    @ToString.Exclude
    private Character character;

    @Builder(builderMethodName = "journalBuilder")
    public Journal(Long id, String entry, LocalDate entryDate, Character character) {
        this.id = id;
        this.entry = entry;
        this.entryDate = entryDate;
        this.character = character;
    }
}
