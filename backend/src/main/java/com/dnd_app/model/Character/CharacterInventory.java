package com.dnd_app.model.Character;

import jakarta.persistence.Embeddable;
//TODO: Rechercher comment implementer le equipe du personnage
@Embeddable
public class CharacterInventory {
    private String equipment;
    private int copperPieces;
    private int silverPieces;
    private int electrumPieces;
    private int goldPieces;
    private int platinumPieces;
}
