package dev.marshallBits.breakingBadApi.services;

import dev.marshallBits.breakingBadApi.dto.CreateUserDTO;
import dev.marshallBits.breakingBadApi.dto.LoginResponseDTO;
import dev.marshallBits.breakingBadApi.models.User;

public interface UserService {
    User registerUser(CreateUserDTO user);
    LoginResponseDTO authenticateUser(CreateUserDTO user);
}
