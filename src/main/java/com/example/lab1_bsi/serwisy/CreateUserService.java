package com.example.lab1_bsi.serwisy;

import com.example.lab1_bsi.dto.UserDto;
import com.example.lab1_bsi.entities.User;
import com.example.lab1_bsi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collection;
import java.util.Random;

@Service
public class CreateUserService implements UserDetailsService {
    private static final Random RANDOM = new SecureRandom();
    @Autowired
    UserRepository userRepository;
    private static final String HMAC_SHA512 = "HmacSHA512";

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
            if(userDto.getIsHMAC()==true){
                //haszujemy hasło przez HMAC
                userModel.setPasswordHash(calculateHMAC(userDto.getPasswordHash(), userDto.getKeyToHMAC()));
            }
            else
            {
                //haszujemy hasło przez SHA512
                userModel.setPasswordHash(calculateSHA512(generateRandomPepper(5)+userDto.getSalt()+userDto.getPasswordHash()));
            }
            return userRepository.save(userModel);
        }
    }

    public static String calculateSHA512(String text)
    {
        //get an instance of SHA-512
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //calculate message digest of the input string - returns byte array
        byte[] messageDigest = md.digest(text.getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        String hashtext = no.toString(16);

        // Add preceding 0s to make it 32 bit
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

        // return the HashText
        return hashtext;
    }

    public static String calculateHMAC(String text, String key){
        Mac sha512Hmac;
        String result="";
        try {
            final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
            sha512Hmac = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
            try {
                sha512Hmac.init(keySpec);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            byte[] macData = sha512Hmac.doFinal(text.getBytes(StandardCharsets.UTF_8));
            result = Base64.getEncoder().encodeToString(macData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login);
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return user.getPasswordHash();
            }

            @Override
            public String getUsername() {
                return user.getLogin();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        return userDetails;
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
}
