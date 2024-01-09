package com.example.creatorconnectbackend.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.example.creatorconnectbackend.models.User;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * UserServiceTest
 * 
 * A test class for the UserService. This class aims to test various functionalities 
 * associated with user operations such as password reset, user data retrieval, and more.
 * 
 * Functions:
 * 1. setUp(): Initializes the testing environment.
 * 2. testGetUserRowMapper(): Tests the functionality that maps rows from the database to User objects.
 * 3. testForgotPassword(): Tests the 'forgot password' functionality.
 * 4. testResetPassword(): Tests the password reset functionality using a token.
 * 
 * Note: Always ensure that the test suite is run after making any changes to the associated methods
 * to validate consistent functionality.
 */

public class UserServiceTest {
	
    private UserService userService;
    
    KeyHolder mockKeyHolder = Mockito.mock(KeyHolder.class);

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(jdbcTemplate, emailService);
    }
    
    @Test
    void testGetUserRowMapper() {
        // Arrange
        UserService userService = new UserService(jdbcTemplate, emailService);

        // Act
        RowMapper<User> rowMapper = userService.getUserRowMapper();

        // Assert
        assertNotNull(rowMapper);

        ResultSet resultSet = mock(ResultSet.class);
        try {
            when(resultSet.getLong("userID")).thenReturn(1L);
            when(resultSet.getString("email")).thenReturn("test@example.com");
            when(resultSet.getString("password")).thenReturn("password");
            when(resultSet.getString("user_type")).thenReturn("user");

            User user = rowMapper.mapRow(resultSet, 1);
            assertEquals(1L, user.getUserID());
            assertEquals("test@example.com", user.getEmail());
            assertEquals("password", user.getPassword());
            assertEquals("user", user.getUser_type());
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @Test
    public void testForgotPassword() {
        String email = "test@example.com";
        when(jdbcTemplate.queryForObject(any(String.class), eq(Integer.class), eq(email))).thenReturn(1);

        userService.forgotPassword(email);

        verify(jdbcTemplate).update(any(String.class), any(String.class), any(String.class));
        verify(emailService).sendEmail(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void testResetPassword() {
        String token = UUID.randomUUID().toString();
        String newPassword = "newPassword";

        userService.resetPassword(token, newPassword);

        verify(jdbcTemplate).update(any(String.class), any(String.class), any(String.class));
    }
}
