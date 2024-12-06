package dev.formation.user_service.service;

import dev.formation.user_service.dto.UserDTO;
import dev.formation.user_service.mapper.UserMapper;
import dev.formation.user_service.model.User;
import dev.formation.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // Importation nécessaire pour encoder les mots de passe
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // Injectez PasswordEncoder

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Cryptage du mot de passe
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userDTO.getUsername());
                    // Cryptage du mot de passe avant de le sauvegarder
                    existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                    User updatedUser = userRepository.save(existingUser);
                    return userMapper.toDTO(updatedUser);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
