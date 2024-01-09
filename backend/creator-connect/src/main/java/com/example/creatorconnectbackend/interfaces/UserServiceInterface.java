package com.example.creatorconnectbackend.interfaces;

import com.example.creatorconnectbackend.models.User;

import java.util.Map;

public interface UserServiceInterface {


    Map<String, Object> userRegister(User user);


    Map<String, Object> userLogin(User user);

    Map<String, Object> forgotPassword(String email);

    void resetPassword(String token, String newPassword);
}
