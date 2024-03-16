package com.dnd_app.dto;

import com.dnd_app.model.Character.Character;
import com.dnd_app.model.Character.CharacterAbilityScores;
import com.dnd_app.model.Character.CharacterSkills;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CharacterDTO {

    private Long id;

    private String name;
    private String characterClass;
    private int level;
    private String background;
    private String race;
    private String alignment;
    private int experiencePoints;

    private CharacterAbilityScores characterAbilityScores;
    private CharacterSkills characterSkills;


    public CharacterDTO(Character character) {
        this.id = character.getId();
        this.name = character.getName();
        this.characterClass = character.getCharacterClass();
        this.level = character.getLevel();
        this.background = character.getBackground();
        this.race = character.getRace();
        this.alignment = character.getAlignment();
        this.experiencePoints = character.getExperiencePoints();
        this.characterAbilityScores = character.getCharacterAbilityScores();
        this.characterSkills = character.getCharacterSkills();
    }

    @Builder(builderMethodName = "characterDTOBuilder")
    public CharacterDTO(Long id, String name, String characterClass, int level, String background, String race, String alignment, int experiencePoints,
                        CharacterAbilityScores characterAbilityScores, CharacterSkills characterSkills) {
        this.id = id;
        this.name = name;
        this.characterClass = characterClass;
        this.level = level;
        this.background = background;
        this.race = race;
        this.alignment = alignment;
        this.experiencePoints = experiencePoints;
        this.characterAbilityScores = characterAbilityScores;
        this.characterSkills = characterSkills;
    }

    public Character fromDTO(){
        return Character.characterBuilder()
                .name(name)
                .characterClass(characterClass)
                .level(level)
                .background(background)
                .race(race)
                .alignment(alignment)
                .experiencePoints(experiencePoints)
                .characterAbilityScores(characterAbilityScores)
                .characterSkills(characterSkills)
                .build();
    }
}
