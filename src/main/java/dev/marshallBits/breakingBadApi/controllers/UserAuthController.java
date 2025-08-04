package dev.marshallBits.breakingBadApi.controllers;

import dev.marshallBits.breakingBadApi.dto.CreateUserDTO;
import dev.marshallBits.breakingBadApi.dto.LoginResponseDTO;
import dev.marshallBits.breakingBadApi.models.User;
import dev.marshallBits.breakingBadApi.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signup(@RequestBody CreateUserDTO user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@RequestBody CreateUserDTO user){
        return userService.authenticateUser(user);
    }
}
