package com.dnd_app.model;

import com.dnd_app.model.Character.Character;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Campaign {
    @Id
    @SequenceGenerator(name = "camp_gen", sequenceName = "camp_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "camp_gen")
    private Long id;

    private String name;

    private String notes;

    private boolean finished;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "campaign_character", joinColumns = @JoinColumn(name = "character_id"), inverseJoinColumns = @JoinColumn(name = "campaign_id"))
   // @JoinColumn(name = "character_id")
    @ToString.Exclude
    private List<Character> characters = new ArrayList<Character>();

    @Builder(builderMethodName = "campaignBuilder")
    public Campaign(Long id,String name, String notes, Client client) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.client = client;
        this.finished = false;
    }
}
