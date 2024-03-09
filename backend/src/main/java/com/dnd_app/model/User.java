package com.dnd_app.model;

import com.dnd_app.model.Character.Character;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorColumn(name = "current_role")
public class User {
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

    protected ERole currentRole;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Character> characters;

    @OneToMany(mappedBy = "user")
    private List<Campaign> campaigns;

    @Builder(builderMethodName = "userBuilder")
    public User(Long id, String username, String email, String password, ERole currentRole) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.currentRole = currentRole;
    }


}
