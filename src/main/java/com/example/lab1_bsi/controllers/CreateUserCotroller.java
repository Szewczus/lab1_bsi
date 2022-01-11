package com.example.lab1_bsi.controllers;

import com.example.lab1_bsi.dto.UserDto;
import com.example.lab1_bsi.entities.User;
import com.example.lab1_bsi.serwisy.SingletonPasswordStore;
import com.example.lab1_bsi.serwisy.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

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
        SingletonPasswordStore singletonPasswordStore = SingletonPasswordStore.getInstance();
        String ip = singletonPasswordStore.getIp();
        LocalDateTime time = singletonPasswordStore.getLoggingTime();
        boolean zalogowany = singletonPasswordStore.isLoggedIn();
        StringBuilder stringBuilder = new StringBuilder();
        if(zalogowany){
            stringBuilder.append("Login successful at: " + time.toString());
        }
        else{
            stringBuilder.append("Login faile at: " + time.toString());
        }
        stringBuilder.append(" Ip: " + ip);

        return stringBuilder.toString();
    }

//    @GetMapping("/")
//    public String redirectWithUsingRedirectView(
//            RedirectAttributes attributes) {
//        return "Jestes zalogowany :)";
//    }

    @PostMapping("/change/userPassword/{password}")
    ResponseEntity changeUserPassword(@PathVariable(name = "password")String password){
        String newPassword = userService.changePassword(password);
        return ResponseEntity.ok(newPassword);
    }

}
