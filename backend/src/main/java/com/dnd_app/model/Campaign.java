package com.dnd_app.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Campaign {
    @Id
    @SequenceGenerator(name = "camp_gen", sequenceName = "camp_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "camp_gen")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;

    @OneToOne
    @JoinColumn(name = "parti_id")
    @ToString.Exclude
    private Party party;

    @Builder(builderMethodName = "campaignBuilder")
    public Campaign(String name, Client client, Party party) {
        this.name = name;
        this.client = client;
        this.party = party;
    }
}
