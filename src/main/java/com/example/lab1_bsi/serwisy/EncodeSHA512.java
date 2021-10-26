package com.example.lab1_bsi.serwisy;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class EncodeSHA512 implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
//        SingletonPasswordStore singletonPasswordStore = SingletonPasswordStore.getInstance();
//        singletonPasswordStore.setPassword(charSequence.toString());
        return calculateSHA512(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encode(charSequence).equals(s);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }

    public String calculateSHA512(String text)
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
}
