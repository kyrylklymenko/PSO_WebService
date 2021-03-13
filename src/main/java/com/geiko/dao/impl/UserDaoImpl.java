package com.geiko.dao.impl;


import com.geiko.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int activateUser(String key) {
        return jdbcTemplate.update("UPDATE user SET activated = 1 WHERE activation_key = ?", new Object[]{key});
    }
}
