package dev.formation.user_service.controller;


import dev.formation.user_service.dto.UserDTO;
import dev.formation.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        // Vérification via le token JWT
        String currentUsername = getCurrentUsername();

        return userService.getUserById(id)
                .filter(user -> user.getUsername().equals(currentUsername)) // Vérifie si l'utilisateur demandé est le même que celui du token
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(403).build()); // Retourne un 403 si non autorisé
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        // Vérification via le token JWT
        String currentUsername = getCurrentUsername();

        return userService.getUserById(id)
                .filter(user -> user.getUsername().equals(currentUsername)) // Vérifie si l'utilisateur est valide
                .map(existingUser -> ResponseEntity.ok(userService.updateUser(id, userDTO)))
                .orElse(ResponseEntity.status(403).build()); // Retourne un 403 si non autorisé
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // Vérification via le token JWT
        String currentUsername = getCurrentUsername();

        return userService.getUserById(id)
                .filter(user -> user.getUsername().equals(currentUsername)) // Vérifie si l'utilisateur a le droit de supprimer
                .map(user -> {
                    userService.deleteUser(id);
                    return ResponseEntity.noContent().<Void>build(); // Spécifie explicitement Void
                })
                .orElse(ResponseEntity.status(403).<Void>build()); // Spécifie explicitement Void
    }


    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }
}


