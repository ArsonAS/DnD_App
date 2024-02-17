package com.dnd_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Character {
    @Id
    @SequenceGenerator(name = "character_sequence", sequenceName = "char_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_sequence")
    private Long id;
}
