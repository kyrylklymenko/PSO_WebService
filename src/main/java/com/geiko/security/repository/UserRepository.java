package com.geiko.security.repository;


import com.geiko.security.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(user) > 0 THEN true ELSE false END FROM User user WHERE user.email = :email")
    boolean existsByEmail(@Param(value = "email") String email);

    @Query("SELECT u.activated from User u where u.activationKey = :activationKey")
    Boolean isAccountActivated(@Param(value = "activationKey") String activationKey);
}
