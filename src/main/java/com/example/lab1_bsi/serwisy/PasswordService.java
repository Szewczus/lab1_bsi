package com.example.lab1_bsi.serwisy;

import com.example.lab1_bsi.dto.PasswordDto;
import com.example.lab1_bsi.entities.Password;
import com.example.lab1_bsi.entities.User;
import com.example.lab1_bsi.repo.PasswordRepository;
import com.example.lab1_bsi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PasswordService {
    @Autowired
    PasswordRepository passwordRepository;

    @Autowired
    UserRepository userRepository;

    public Password savePassword(PasswordDto passwordDto){
        //wyciagam hasło mastera z singletona w którym zapisałam dane przed zaodowaniem hasła
        String masterpassword = getMasterPassword();
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

    public String decodePassword(String password){
        String masterpassword = getMasterPassword();
        try {
            return EncoderAESenc.decodeWithGenerate(password, masterpassword);
        }
        catch (Exception exception){
            Logger.getLogger(PasswordService.class.getName()).log(Level.SEVERE, null, exception);
        }
        return null;
    }

    private String getMasterPassword() {
        SingletonPasswordStore singletonPasswordStore = SingletonPasswordStore.getInstance();
        return singletonPasswordStore.getPassword();
    }

    public List<Password> getPasswords(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String loginFromSession = principal.getUsername();
            User userFromSession =  userRepository.findUserByLogin(loginFromSession);//pobieram użytkownika z sesji
            Long userFK = userFromSession.getId();
            return passwordRepository.findPasswordsById(userFK);
        }
        return null;
    }
}
