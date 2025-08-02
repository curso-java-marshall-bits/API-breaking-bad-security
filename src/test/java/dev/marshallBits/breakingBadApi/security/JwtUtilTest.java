package dev.marshallBits.breakingBadApi.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @Test
    void generateToken() {
        String token = jwtUtil.generateToken("PacoPaquito", "ROLE_ADMIN");

        System.out.println(token);
        assertNotNull(token);
    }
}
