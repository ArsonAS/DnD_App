package com.dnd_app.model;

import com.dnd_app.model.Character.Character;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
public class Party {
    @Id
    @SequenceGenerator(name = "parti_gen", sequenceName = "parti_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parti_gen")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "character_1_id")
    private Character character1;

    @ManyToOne
    @JoinColumn(name = "character_2_id")
    private Character character2;

    @ManyToOne
    @JoinColumn(name = "character_3_id")
    private Character character3;

    @ManyToOne
    @JoinColumn(name = "character_4_id")
    private Character character4;

    @ManyToOne
    @JoinColumn(name = "character_5_id")
    private Character character5;

    @Builder(builderMethodName = "partyBuilder")
    public Party(Character character1, Character character2, Character character3, Character character4, Character character5) {
        this.character1 = character1;
        this.character2 = character2;
        this.character3 = character3;
        this.character4 = character4;
        this.character5 = character5;
    }
}
