package com.dnd_app.model.Character;

import jakarta.persistence.Embeddable;

@Embeddable
public class CharacterDetails {
    private String personalityTraits;
    private String ideals;
    private String bonds;
    private String flaws;
    private String featuresAndTraits;
}
