package com.example.lab1_bsi.repo;

import com.example.lab1_bsi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Boolean existsUserByLogin(String login);
    public User findUserByLogin(String login);
    public User findUserById(Long id);
}
