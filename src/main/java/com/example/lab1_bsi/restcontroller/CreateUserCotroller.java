package com.example.lab1_bsi.restcontroller;

import com.example.lab1_bsi.dto.UserDto;
import com.example.lab1_bsi.entities.User;
import com.example.lab1_bsi.serwisy.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class CreateUserCotroller {
    @Autowired
    CreateUserService createUserService;

    @PostMapping("/create/user")
    ResponseEntity saveUser(@RequestBody UserDto userDto){
        User userModel = createUserService.saveUser(userDto);
        return ResponseEntity.ok(userModel);
    }
    @GetMapping("/")
    public String redirectWithUsingRedirectView(
            RedirectAttributes attributes) {
        return "Jestes zalogowany :)";
    }

}
