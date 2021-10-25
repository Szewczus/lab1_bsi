package com.example.lab1_bsi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String login;
    private String passwordHash;
    private String salt;
    private Boolean isHMAC;
    private String keyToHMAC;
}
