package com.example.lab1_bsi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    @Column(name = "web_address")
    private String webAddress;
    private String description;
    private String login;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

}