package com.dnd_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Parti {
    @Id
    @SequenceGenerator(name = "parti_gen", sequenceName = "parti_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parti_gen")
    private Long id;



}
