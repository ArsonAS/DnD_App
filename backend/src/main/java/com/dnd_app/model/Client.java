package com.dnd_app.model;

import com.dnd_app.model.Character.Character;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @SequenceGenerator(name = "user_gen", sequenceName = "user_sec", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    protected Long id;
    @Column(unique = true)
    protected String username;
    @Column(unique = true)
    protected String email;
    @ToString.Exclude
    protected String password;
    protected String role;

    @ToString.Exclude
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Character> characters;

    @ToString.Exclude
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Campaign> campaigns;

    @Builder(builderMethodName = "clientBuilder")
    public Client(Long id, String username, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }


}
