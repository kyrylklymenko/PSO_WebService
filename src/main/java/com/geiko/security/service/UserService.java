package com.geiko.security.service;

import com.geiko.security.model.User;

import java.util.List;

public interface UserService {
    User registerNewUser(User user, String baseUrl);
    boolean isUserExists(String email);
    boolean activateAccount(String activationKey);
    boolean isAccountActivated(String activationKey);
    List<User> getAll();
    User getById(Long userId);

}
