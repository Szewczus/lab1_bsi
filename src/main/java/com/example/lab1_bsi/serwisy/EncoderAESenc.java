package com.example.lab1_bsi.serwisy;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncoderAESenc {
    private static final String ALGO = "AES";
    private static final byte[] keyValue
            = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
    //encrypts string and returns encrypted string
    public static String encrypt(String data, Key key) throws Exception {
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }
    //decrypts string and returns plain text
    public static String decrypt(String encryptedData, Key key) throws Exception {
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }
    // Generate a new encryption key.
    public static Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }

    public static String encodeWithGenerate(String password){
        Key key = null;
        try {
            key = generateKey();
            String encrypted = encrypt(password, key);
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static void main(String args[]) {
//        try {
//            String text = "My secret text.";
//            Key key = generateKey();
//            String encrypted = encrypt(text, key);
//            System.out.println("encrypted: " + encrypted);
//            String decrypted = decrypt(encrypted, key);
//            System.out.println("decrytped: " + decrypted);
//        } catch (Exception ex) {
//            Logger.getLogger(EncoderAESenc.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}
