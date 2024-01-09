package com.example.creatorconnectbackend.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.creatorconnectbackend.models.EmailBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.example.creatorconnectbackend.models.User;
import com.example.creatorconnectbackend.services.UserService;


@RestController

@CrossOrigin

@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
     * Class constructor for UserController.
     *
     * @param userService service class for executing user operations.
     */
	public UserController(UserService userService) {
        this.userService = userService;
    }

	/**
     * Handles POST requests to register a new user.
     *
     * @param user user object to register.
     * @param bindingResult object holding the result of the validation process.
     * @return response entity containing the registration status or the validation errors.
     */
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
		logger.info("Attempt to register a new user.");
		if (bindingResult.hasErrors()) {
			// Convert the list of ObjectError objects into a single string and log the error messages.
			String errorMsg = bindingResult.getAllErrors().stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(", "));
			Map<String, Object> response = new HashMap<>();
			response.put("ok", false);
			response.put("message", "Binding error: " + errorMsg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		// Register the user and retrieve the response.
		Map<String, Object> map = userService.userRegister(user);
		logger.info("User registered successfully with ID: {}", user.getUserID());
		return ResponseEntity.ok(map);
	}
	
	/**
     * Handles POST requests for user login.
     *
     * @param user user object with username and password.
     * @param bindingResult object holding the result of the validation process.
     * @return response entity containing the login status or the validation errors.
     */
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@Valid @RequestBody User user, BindingResult bindingResult) {
		logger.info("Attempt to login user.");
		if (bindingResult.hasErrors()) {
			// Convert the list of ObjectError objects into a single string and log the error messages.
	        String errorMsg = bindingResult.getAllErrors().stream()
	            .map(ObjectError::getDefaultMessage)
	            .collect(Collectors.joining(", "));
			Map<String, Object> map1 = new HashMap<>();
			map1.put("ok", "false");
			map1.put("message", "Binding error!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map1);
	    }
		Map<String, Object> map = userService.userLogin(user);
		boolean loggedIn = (boolean) map.get("ok");
	    if (!loggedIn) {
	        logger.info("User login failed");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
	    } else {
	        logger.info("User login successful");
	        return ResponseEntity.ok(map);
	    }
	}

	/**
     * Handles POST requests for forgotten password cases. Sends reset password link to the user's email.
     *
     * @param emailBody email object containing user's email.
     * @return response entity containing the status of the password reset request.
     */
	@PostMapping("/forgot-password")
	public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody EmailBody emailBody) {
		logger.info("Processing forgot password request for email: {}", emailBody.getEmail());
		// Send reset password link to the user's email.
		Map<String, Object> map = userService.forgotPassword(emailBody.getEmail());
		return ResponseEntity.ok(map);
	}

	/**
     * Handles POST requests for forgotten password cases. Sends reset password link to the user's email.
     *
     * @param request: a map object containing user's new email and a token.
     * @return response entity containing the status of the password reset request.
     */
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		logger.info("Processing password reset request for token: {}", token);
		// Set the user's password to the new password.
		String newPassword = request.get("password");
		userService.resetPassword(token, newPassword);
		return ResponseEntity.ok("Password has been reset");
	}
}
