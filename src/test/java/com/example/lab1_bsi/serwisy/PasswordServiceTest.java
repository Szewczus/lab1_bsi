package com.example.lab1_bsi.serwisy;

import com.example.lab1_bsi.dto.PasswordDto;
import com.example.lab1_bsi.entities.User;
import com.example.lab1_bsi.repo.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PasswordServiceTest {

    @BeforeAll
    public static void setUp(){
        SingletonPasswordStore singletonPasswordStore = SingletonPasswordStore.getInstance();
        singletonPasswordStore.setPassword("ewus123");
    }

    @Autowired
    UserRepository userRepository;


    @Test
    void encodePassword() {
        PasswordService passwordService = new PasswordService(userRepository);
        String passwordEncoded = passwordService.encodePassword("password", "masterpassword");
        assertNotEquals(passwordEncoded, null);
    }


    @Test
    void decodePassword() {
        PasswordService passwordService = new PasswordService(userRepository);
        String decodedPass=passwordService.decodePassword("rwlBwSviX1A8rp5E2IZa8A==");
        assertNotEquals(decodedPass, null);
    }

}