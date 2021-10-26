package com.example.lab1_bsi.serwisy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeMD5 {
    public byte[] calculateMD5(String text) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] messageDigest = md.digest(text.getBytes());
            return messageDigest;
    }
}
