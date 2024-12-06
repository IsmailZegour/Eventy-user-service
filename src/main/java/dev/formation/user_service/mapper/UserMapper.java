package dev.formation.user_service.mapper;


import dev.formation.user_service.dto.UserDTO;
import dev.formation.user_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}

