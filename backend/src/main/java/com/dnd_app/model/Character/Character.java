package com.dnd_app.model.Character;

import com.dnd_app.model.Client;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Character {
    @Id
    @SequenceGenerator(name = "char_gen", sequenceName = "char_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "char_gen")
    private Long id;

    private String name;
    private String characterClass;
    private String subclass;
    private int level;
    private String background;
    private String race;
    private String alignment;
    private int experiencePoints;

    @Embedded
    CharacterDetails characterDetails;
    @Embedded
    CharacterAbilityScores characterAbilityScores;
    @Embedded
    CharacterSkills characterSkills;
    @Embedded
    CharacterInventory characterInventory;


    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;





}
