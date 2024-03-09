package com.dnd_app.dto;

import com.dnd_app.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UserDTO {
    protected Long id;
    protected String username;
    protected String email;
    protected String password;


    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    @Builder(builderMethodName = "userDTOBuilder")
    public UserDTO(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User fromDTO(){
        return User.userBuilder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }




}
