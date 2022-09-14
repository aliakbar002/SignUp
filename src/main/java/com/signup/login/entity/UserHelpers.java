package com.signup.login.entity;

import java.util.Base64;

public class UserHelpers {
    public static String encryptStringToBase64(String text) throws Exception {
        String base64String = "";
        try {
            base64String = new String(Base64.getEncoder().encode(text.getBytes()));
        } catch (Exception e) {
            throw new Exception("Unable to encrypt password");
        }

        return base64String;
    }


    public static String decryptStringToBase64(String text) throws Exception {
        String base64String = "";
        try {
            base64String = new String(Base64.getDecoder().decode(text.getBytes()));
        } catch (Exception e) {
            throw new Exception("Unable to decrypt password");
        }

        return base64String;
    }
}
