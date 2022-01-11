package com.example.lab1_bsi.serwisy;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EncoderHMAC  implements PasswordEncoder {
    private static final Random RANDOM = new SecureRandom();
    private static final String HMAC_SHA512 = "HmacSHA512";
    private static final Logger log =  Logger.getLogger("EncoderHMAC");

    @Override
    public String encode(CharSequence charSequence) {
        SingletonPasswordStore singletonPasswordStore = SingletonPasswordStore.getInstance();
        singletonPasswordStore.setPassword(charSequence.toString());
        return calculateHMAC(charSequence.toString(), "generateRandomKey(6)" );
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        boolean matches = encode(charSequence).equals(s);
        SingletonPasswordStore singletonPasswordStore = SingletonPasswordStore.getInstance();
        if(matches){
            System.out.println("zalogowany");
            singletonPasswordStore.setLoggedIn(true);
        }
        else {
            System.out.println("bledny login lub hasło");
            singletonPasswordStore.setLoggedIn(false);
        }

        LocalDateTime localDateTime =LocalDateTime.now();
        singletonPasswordStore.setLoggingTime(localDateTime);
        try {
            singletonPasswordStore.setIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            log.log( Level.SEVERE, "error:", e );
        }
        return matches;
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }

    public String calculateHMAC(String text, String key){
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

    public static String generateRandomKey(int length) {
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
