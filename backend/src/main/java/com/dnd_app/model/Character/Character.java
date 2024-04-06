package com.dnd_app.model.Character;

import com.dnd_app.model.Campaign;
import com.dnd_app.model.Client;
import com.dnd_app.model.Journal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Character {
    @Id
    @SequenceGenerator(name = "char_gen", sequenceName = "char_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "char_gen")
    private Long id;

    private String name;
    private String classe;
    private int level;
    private String background;
    private String race;
    private String alignment;
    private int experiencePoints;

    @Embedded
    CharacterAbilityScores characterAbilityScores;
    @Embedded
    CharacterSkills characterSkills;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;


    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "characters")
    //@JoinColumn(name = "campaign_id")
    @ToString.Exclude
    private List<Campaign> campaigns = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
    private List<Journal> journalEntries;


    @Builder(builderMethodName = "characterBuilder")
    public Character(Long id, String name, String classe, int level, String background, String race, String alignment, int experiencePoints,
                     CharacterAbilityScores characterAbilityScores, CharacterSkills characterSkills, Client client) {
        this.id = id;
        this.name = name;
        this.classe = classe;
        this.level = level;
        this.background = background;
        this.race = race;
        this.alignment = alignment;
        this.experiencePoints = experiencePoints;
        this.characterAbilityScores = characterAbilityScores;
        this.characterSkills = characterSkills;
        this.client = client;
    }


}
