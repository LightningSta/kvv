package com.natsumi.kv.repository;

import com.natsumi.kv.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    User getUserById(Integer id);
    User getUserBylogin(String login);
    boolean existsUserBylogin(String login);
}