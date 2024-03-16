package com.dnd_app.dto;

import com.dnd_app.model.Client;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ClientDTO {
    protected Long id;
    protected String username;
    protected String email;
    protected String password;
    private String role;


    public ClientDTO(Client client) {
        this.id = client.getId();
        this.username = client.getUsername();
        this.email = client.getEmail();
        this.password = client.getPassword();
        this.role = client.getRole();
    }

    @Builder(builderMethodName = "clientDTOBuilder")
    public ClientDTO(Long id, String username, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Client fromDTO(){
        return Client.clientBuilder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}
