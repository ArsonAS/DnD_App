package com.dnd_app.controller;

import com.dnd_app.dto.ClientDTO;
import com.dnd_app.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;


    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getEmployerById(@PathVariable Long id){
        return clientService.findClientById(id)
                .map(client -> ResponseEntity.ok(client))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
