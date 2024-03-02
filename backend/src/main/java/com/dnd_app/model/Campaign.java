package com.dnd_app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Campaign {
    @Id
    @SequenceGenerator(name = "camp_gen", sequenceName = "camp_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "camp_gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dungeon_master_id")
    @ToString.Exclude
    DM dungeonMaster;
}
