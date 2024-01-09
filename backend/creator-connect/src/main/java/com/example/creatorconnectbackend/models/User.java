package com.example.creatorconnectbackend.models;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {


    private Long userID;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email should be valid")
    private String email;

    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;

    @Pattern(regexp="^(Influencer|Organization)$", message = "User type must be either Influencer or Organization")
    private String user_type;

    private String reset_token;

    // Getters and Setters

    /**
     * @return the unique identifier of the user.
     */
    public Long getUserID() {
        return userID;
    }

    /**
     * @param userID the unique identifier to set for the user.
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /**
     * @return the email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email address to set for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password of the user account.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set for the user account.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the type of user (either "Influencer" or "Organization").
     */
    public String getUser_type() {
        return user_type;
    }

    /**
     * @param user_type the type of user to set (either "Influencer" or "Organization").
     */
    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    /**
     * @return the reset token used for password reset functionality.
     */
    public String getReset_token() {
        return reset_token;
    }

    /**
     * @param reset_token the reset token to set for password reset functionality.
     */
    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }
}
