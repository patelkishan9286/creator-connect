package com.example.creatorconnectbackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.example.creatorconnectbackend.models.EmailBody;
import com.example.creatorconnectbackend.models.User;
import com.example.creatorconnectbackend.services.UserService;

/**
 * UserControllerTest
 *
 * This class provides unit tests for the UserController.
 * It mocks the service layer interactions to ensure that the user-related API endpoints 
 * respond correctly for different inputs and scenarios.
 * 
 * Functions:
 * 1. setup() - Initializes mock objects and instances for testing.
 * 2. testRegisterUser() - Tests successful user registration without binding errors.
 * 3. testLoginUserFail() - Tests unsuccessful user login due to invalid credentials.
 * 4. testRegisterUserWithBindingErrors() - Tests user registration with binding result errors.
 * 5. testLoginUser() - Tests successful user login.
 * 6. testLoginUserWithBindingErrors() - Tests user login with binding result errors.
 * 7. testForgotPassword() - Tests the forgot password functionality with a valid email.
 * 8. testResetPassword() - Tests the password reset functionality.
 * 
 */

public class UserControllerTest {
    private UserService userService;
    private UserController userController;

    @BeforeEach
    public void setup() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setUserID(1L);
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");

        when(userService.userRegister(user)).thenReturn(Collections.singletonMap("ok", true));

        ResponseEntity<Map<String, Object>> response = userController.registerUser(user, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
    }

    @Test
    public void testLoginUserFail() {
        User testUser = new User();
        testUser.setUserID(123L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");

        BindingResult bindingResult = new BeanPropertyBindingResult(testUser, "testUser");

        Map<String, Object> map = new HashMap<>();
        map.put("ok", false);
        map.put("message", "Invalid credentials");
        when(userService.userLogin(testUser)).thenReturn(map);

        ResponseEntity<Map<String, Object>> response = userController.loginUser(testUser, bindingResult);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
        assertEquals("Invalid credentials", response.getBody().get("message"));
    }

    @Test
    public void testRegisterUserWithBindingErrors() {
        User user = new User();
        user.setUserID(1L);
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
        bindingResult.rejectValue("userID", "error.userID", "User ID is not valid");

        ResponseEntity<Map<String, Object>> response = userController.registerUser(user, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody().get("ok"));
    }

    @Test
    public void testLoginUser() {
        User user = new User();
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");

        when(userService.userLogin(user)).thenReturn(Collections.singletonMap("ok", true));

        ResponseEntity<Map<String, Object>> response = userController.loginUser(user, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
    }

    @Test
    public void testLoginUserWithBindingErrors() {
        User user = new User();
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
        bindingResult.rejectValue("userID", "error.userID", "User ID is not valid");

        ResponseEntity<Map<String, Object>> response = userController.loginUser(user, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("false", response.getBody().get("ok"));
    }

    @Test
    public void testForgotPassword() {
        EmailBody emailBody = new EmailBody();
        emailBody.setEmail("test@example.com");

        when(userService.forgotPassword("test@example.com")).thenReturn(Collections.singletonMap("ok", true));

        ResponseEntity<Map<String, Object>> response = userController.forgotPassword(emailBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("ok"));
    }

    @Test
    public void testResetPassword() {
        ResponseEntity<String> response = userController.resetPassword(Collections.singletonMap("token", "test-token"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password has been reset", response.getBody());
    }
}
