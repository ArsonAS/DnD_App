package com.dnd_app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@ToString(callSuper = true)
@Data
@NoArgsConstructor
public class Player extends User{

    @ToString.Exclude
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    List<Character> characters;
}
