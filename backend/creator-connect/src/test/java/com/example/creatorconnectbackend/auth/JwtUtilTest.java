package com.example.creatorconnectbackend.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    private SecretKey secretKey;

    @BeforeEach
    public void setUp() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate a secure secret key
        jwtUtil = new JwtUtil(); // Initialize JwtUtil instance

        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        ReflectionTestUtils.setField(jwtUtil, "SECRET_KEY", encodedKey);
        // Set the secret key in JwtUtil instance using ReflectionTestUtils
       // ReflectionTestUtils.setField(jwtUtil, "SECRET_KEY", secretKey.getEncoded());
    }

    @Test
    public void whenValidToken_thenEmailShouldExtractCorrectly() {
        String token = jwtUtil.generateToken("test@example.com");
        assertEquals("test@example.com", jwtUtil.extractEmail(token));
    }

    @Test
    public void whenValidToken_thenExpirationDateShouldExtractCorrectly() {
        String token = jwtUtil.generateToken("test@example.com");
        Date expectedExpirationDate = jwtUtil.extractExpiration(token);
        assertFalse(expectedExpirationDate.before(new Date()));
    }

    @Test
    public void whenValidToken_thenShouldNotBeExpired() {
        String token = jwtUtil.generateToken("test@example.com");
        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    public void whenValidToken_thenShouldValidateCorrectly() {
        String token = jwtUtil.generateToken("test@example.com");
        assertTrue(jwtUtil.validateToken(token, "test@example.com"));
    }
}
