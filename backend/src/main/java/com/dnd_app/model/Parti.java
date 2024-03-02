package com.dnd_app.model;

import com.dnd_app.model.Character.Character;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Parti {
    @Id
    @SequenceGenerator(name = "parti_gen", sequenceName = "parti_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parti_gen")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "character_1_id")
    Character character1;

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

}
