package dev.marshallBits.breakingBadApi.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncoderTest {

    // para tener acceso al password encoder sin tener que ejecutar toda la app
    private final SecurityConfig securityConfig = new SecurityConfig();
    private final PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

    @Test
    void testEncoder() {
        String rawPassword = "Hello123";

        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Este es el password original: " + rawPassword);
        System.out.println("Este es el password codificado: " + encodedPassword.length());
        System.out.println("Password correcto: " + passwordEncoder.matches("Hesdallo123", encodedPassword));
        assertTrue(passwordEncoder.matches("Hesdallo123", encodedPassword));
    }
}
