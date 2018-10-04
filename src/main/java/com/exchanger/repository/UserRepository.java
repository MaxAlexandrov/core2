package com.exchanger.repository;

import com.exchanger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByLogin(String login);
    User findByActivationCode(String code);
}
