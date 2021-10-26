package com.example.lab1_bsi.controllers;

import com.example.lab1_bsi.dto.PasswordDto;
import com.example.lab1_bsi.dto.UserDto;
import com.example.lab1_bsi.entities.Password;
import com.example.lab1_bsi.entities.User;
import com.example.lab1_bsi.serwisy.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PasswordController {
    @Autowired
    PasswordService passwordService;

    @PostMapping("/create/password")
    ResponseEntity saveUser(@RequestBody PasswordDto passwordDto){
        Password password = passwordService.savePassword(passwordDto);
        return ResponseEntity.ok(password);
    }
}
