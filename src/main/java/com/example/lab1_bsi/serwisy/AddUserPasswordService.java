package com.example.lab1_bsi.serwisy;

import com.example.lab1_bsi.entities.Password;
import com.example.lab1_bsi.repo.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddUserPasswordService {
    @Autowired
    PasswordRepository passwordRepository;

}
