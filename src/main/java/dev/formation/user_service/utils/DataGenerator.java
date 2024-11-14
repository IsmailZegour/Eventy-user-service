package dev.formation.user_service.utils;


import com.github.javafaker.Faker;
import dev.formation.user_service.dto.UserDTO;
import dev.formation.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataGenerator implements CommandLineRunner {

    private final UserService userService;
    private final Faker faker = new Faker(new Random());
    private final Random random = new Random();

    @Override 
    public void run(String... args) throws Exception {
        generateUsers(15);
    }

    public void generateUsers(int count) {
        if (!userService.getAllUsers().isEmpty()) {
            System.out.println("Des utilisateurs existent déjà. Génération annulée.");
            return;
        }
        List<UserDTO> users = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(faker.internet().emailAddress());
            userDTO.setPassword(faker.internet().password()); // Vous pouvez appliquer des règles de complexité si nécessaire

            users.add(userDTO);
        }

        users.forEach(userService::createUser); // Enregistrez les utilisateurs dans la base de données
    }

}

