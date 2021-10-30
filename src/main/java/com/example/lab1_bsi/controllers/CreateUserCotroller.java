package com.example.lab1_bsi.controllers;

import com.example.lab1_bsi.dto.UserDto;
import com.example.lab1_bsi.entities.User;
import com.example.lab1_bsi.serwisy.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class CreateUserCotroller {
    @Autowired
    UserService userService;

    @PostMapping("/create/user")
    ResponseEntity saveUser(@RequestBody UserDto userDto){
        User userModel = userService.saveUser(userDto);
        return ResponseEntity.ok(userModel);
    }
    @GetMapping("/")
    public String redirectWithUsingRedirectView(
            RedirectAttributes attributes) {
        return "Jestes zalogowany :)";
    }

}
