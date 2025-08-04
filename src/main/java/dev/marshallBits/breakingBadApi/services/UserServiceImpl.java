package dev.marshallBits.breakingBadApi.services;

import dev.marshallBits.breakingBadApi.dto.CreateUserDTO;
import dev.marshallBits.breakingBadApi.dto.LoginResponseDTO;
import dev.marshallBits.breakingBadApi.models.User;
import dev.marshallBits.breakingBadApi.models.UserRole;
import dev.marshallBits.breakingBadApi.repositories.UserRepository;
import dev.marshallBits.breakingBadApi.security.JwtUtil;
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

    @Autowired
    JwtUtil jwtUtil;

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

    @Override
    public LoginResponseDTO authenticateUser(CreateUserDTO loginUser) { // Hola1234
        User existingUser = userRepository.findByUsername(loginUser.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos"));

        // la contraseña no es correcta
        // existing user.getPassword será $2a$10$p1MCcwRmLYarybZ5xnm9ZON69I8pKTD5lpfE8d93PcbWUyJmInxFW
        if (!passwordEncoder.matches(loginUser.getPassword(), existingUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }
        // si nuestro código se sigue ejecutando, la contraseña es correcta

        String token = jwtUtil.generateToken(existingUser.getUsername(), existingUser.getRole().name());

        return LoginResponseDTO.builder()
                .token(token)
                .username(existingUser.getUsername())
                .build();

    }
}
