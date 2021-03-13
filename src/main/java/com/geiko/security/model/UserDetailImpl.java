package com.geiko.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

/**
 * Created by Андрей on 15.04.2017.
 */
public class UserDetailImpl extends org.springframework.security.core.userdetails.User {
    private User user;

    public UserDetailImpl(User user) {
        super(user.getEmail(), user.getPassword(), user.isActivated(), true, true, true,  AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return user.getId();
    }

    public Authority getRole() {
        return user.getRole();
    }
}
