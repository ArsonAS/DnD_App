package com.dnd_app;

import com.dnd_app.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
@AllArgsConstructor
public class DnDApplication implements CommandLineRunner {
    private PlayerRepository playerRepository;

    public static void main(String[] args){
        SpringApplication.run(DnDApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }
}
