package com.example.lab1_bsi.repo;

import com.example.lab1_bsi.entities.Password;
import com.example.lab1_bsi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
    public List <Password> findPasswordsById(Long id);
    public List <Password> findPasswordsByUser(User user);
}
