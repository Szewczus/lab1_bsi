package com.example.lab1_bsi.serwisy;

import com.example.lab1_bsi.dto.PasswordDto;
import com.example.lab1_bsi.entities.Password;
import com.example.lab1_bsi.entities.User;
import com.example.lab1_bsi.repo.PasswordRepository;
import com.example.lab1_bsi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    @Autowired
    PasswordRepository passwordRepository;

    @Autowired
    UserRepository userRepository;

    public Password savePassword(PasswordDto passwordDto){
        //wyciagam hasło mastera z singletona w którym zapisałam dane przed zaodowaniem hasła
        SingletonPasswordStore singletonPasswordStore = SingletonPasswordStore.getInstance();
        String masterpassword = singletonPasswordStore.getPassword();

        Password password = new Password();
        //ustawianie zakodowanego hasła użytkownika do przechowania
        password.setPassword(encodePassword(passwordDto.getPassword(), masterpassword));
        password.setDescription(passwordDto.getDescription());
        password.setLogin(passwordDto.getLogin());
        password.setWebAddress(passwordDto.getWebAddress());
        User user = userRepository.findUserById(passwordDto.getUserFK());
        password.setUser(user);
        return passwordRepository.save(password);
    }

    public String encodePassword(String password, String masterPassword){
        try {
            return EncoderAESenc.encodeWithGenerate(password, masterPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
