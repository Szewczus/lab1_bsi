package com.example.lab1_bsi.serwisy;

import com.example.lab1_bsi.dto.UserDto;
import com.example.lab1_bsi.entities.Password;
import com.example.lab1_bsi.entities.User;
import com.example.lab1_bsi.repo.PasswordRepository;
import com.example.lab1_bsi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class UserService implements UserDetailsService {
    private static final Random RANDOM = new SecureRandom();
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordRepository passwordRepository;

    public User saveUser(UserDto userDto){
        if(userRepository.existsUserByLogin(userDto.getLogin())){
           return null;
        }
        else {
            User userModel = new User();
            userModel.setId(userDto.getId());
            userModel.setLogin(userDto.getLogin());
            userModel.setIsHMAC(userDto.getIsHMAC());
            userModel.setSalt(userDto.getSalt());
            userModel.setKeyToHMAC(userDto.getKeyToHMAC());
            if(userDto.getIsHMAC()){
                //haszujemy hasło przez HMAC
                EncoderHMAC encoderHMAC = new EncoderHMAC();
                userModel.setPasswordHash("{hmac}"+encoderHMAC.encode(userDto.getPasswordHash()));
            }
            else
            {
                //haszujemy hasło przez SHA512
                EncodeSHA512 encoder = new EncodeSHA512();
                //userModel.setPasswordHash("{sha512}"+encoder.encode(generateRandomPepper(5)+userDto.getSalt()+userDto.getPasswordHash()));
                userModel.setPasswordHash("{sha512}"+encoder.encode(userDto.getPasswordHash()));
            }
            return userRepository.save(userModel);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPasswordHash(), Collections.EMPTY_LIST);
    }

    public static String generateRandomPepper(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int c = RANDOM.nextInt(62);
            if (c <= 9) {
                sb.append(String.valueOf(c));
            } else if (c < 36) {
                sb.append((char) ('a' + c - 10));
            } else {
                sb.append((char) ('A' + c - 36));
            }
        }
        return sb.toString();
    }

    private String getMasterPassword() {
        SingletonPasswordStore singletonPasswordStore = SingletonPasswordStore.getInstance();
        return singletonPasswordStore.getPassword();
    }

    public String changePassword(String haslo_do_zmiany){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String zakododwaeNoweHaslo="";
        if(authentication !=null){
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String loginFromSession = principal.getUsername();
            String masterPassword = getMasterPassword(); //pobieram aktualne hasło użytkownika (przed zmianą)
            User userFromSession = userRepository.findUserByLogin(loginFromSession);

            String stareHaslo = userFromSession.getPasswordHash(); //zapisanie starego hasla
            String noweHasloUzytkownika ="";
            if(userFromSession.getIsHMAC()==true){
                EncoderHMAC encoderHMAC = new EncoderHMAC();
                noweHasloUzytkownika = "{hmac}"+encoderHMAC.encode(haslo_do_zmiany);
                userRepository.setUserPasswordHash(noweHasloUzytkownika); //zmiana na nowe haslo uzytkownika
            }
            else{
                //haszujemy hasło przez SHA512
                EncodeSHA512 encoder = new EncodeSHA512();
                noweHasloUzytkownika = "{sha512}"+encoder.encode(haslo_do_zmiany);
                userRepository.setUserPasswordHash(noweHasloUzytkownika); //zmiana na nowe haslo uzytkownika
            }
            //pobranie wszystkich haseł
            List<Password> listaHaselUzytkownika = passwordRepository.findPasswordsByUser(userFromSession);

            //odkodowanie haseł starym hasłem i zakodowanie nowym:
            for(Password haslo_uzytkownika : listaHaselUzytkownika){
                String hasloStareZakodowane = haslo_uzytkownika.getPassword();
                PasswordService passwordService = new PasswordService(userRepository);
                String decodedPassword = passwordService.decodePassword(hasloStareZakodowane);
                zakododwaeNoweHaslo = passwordService.encodePassword(decodedPassword, noweHasloUzytkownika);
                passwordRepository.updateUserPassword(zakododwaeNoweHaslo, hasloStareZakodowane);
            }
        }
        return zakododwaeNoweHaslo;
    }

}
