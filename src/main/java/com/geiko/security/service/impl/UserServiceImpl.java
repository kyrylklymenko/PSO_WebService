package com.geiko.security.service.impl;


import com.geiko.dao.UserDao;
import com.geiko.security.model.Authority;
import com.geiko.security.model.User;
import com.geiko.security.repository.UserRepository;
import com.geiko.security.service.MailService;
import com.geiko.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserDao userDao;

    @Override
    public User registerNewUser(User user, String baseUrl) {
        String activationKey = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Authority.ROLE_USER);
        user.setActivationKey(activationKey);
        User savedUser = userRepository.save(user);
        if (!Objects.isNull(savedUser)){
            mailService.sendActivationEmail(savedUser.getEmail(), savedUser.getActivationKey(), savedUser, baseUrl);
        }
        return savedUser;
    }
    @Override
    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean activateAccount(String activationKey) {
        int countUpdates = userDao.activateUser(activationKey);
        if(countUpdates != 0)
            return true;
        return false;
    }

    @Override
    public boolean isAccountActivated(String activationKey) {
        return userRepository.isAccountActivated(activationKey);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findOne(userId);
    }
}
