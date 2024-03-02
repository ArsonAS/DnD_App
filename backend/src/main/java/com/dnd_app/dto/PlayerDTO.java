package com.dnd_app.dto;

import com.dnd_app.model.Player;
import lombok.Builder;
import lombok.Data;

@Data
public class PlayerDTO {
    private Long id;
    private String username;
    private String email;
    private String password;


    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.username = player.getUsername();
        this.email = player.getEmail();
        this.password = player.getPassword();
    }

    @Builder(builderMethodName = "playerDTOBuilder")
    public PlayerDTO(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Player fromDTO(){
        return Player.playerBuilder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
