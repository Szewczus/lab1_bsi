package com.example.lab1_bsi.dto;

import com.example.lab1_bsi.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {
    private Long id;
    private String password;
    private String webAddress;
    private String description;
    private String login;
}
