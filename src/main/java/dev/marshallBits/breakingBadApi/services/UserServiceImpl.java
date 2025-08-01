package dev.marshallBits.breakingBadApi.services;

import dev.marshallBits.breakingBadApi.dto.CreateUserDTO;
import dev.marshallBits.breakingBadApi.models.User;
import dev.marshallBits.breakingBadApi.models.UserRole;
import dev.marshallBits.breakingBadApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(CreateUserDTO user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
        }

        User newUser = new User();
        newUser.setRole(UserRole.ROLE_USER);
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }
}
