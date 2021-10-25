package com.example.lab1_bsi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    @Column(name = "password_hash")
    private String passwordHash;
    private String salt;
    @Column(name = "is_hmac")
    private Boolean isHMAC;
    @Column(name = "key_to_HMAC")
    private String keyToHMAC;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Password>passwordSet;
}
