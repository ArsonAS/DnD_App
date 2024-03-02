package com.dnd_app.model.Character;

import jakarta.persistence.Embeddable;

@Embeddable
public class CharacterAbilityScores {
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
}
