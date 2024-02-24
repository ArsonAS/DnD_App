package com.dnd_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Character {
    @Id
    @SequenceGenerator(name = "char_gen", sequenceName = "char_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "char_gen")
    private Long id;
}