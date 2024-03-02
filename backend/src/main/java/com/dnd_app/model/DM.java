package com.dnd_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class DM extends User{

    @OneToMany(mappedBy = "dm")
    private List<Campaign> campaigns;
}
