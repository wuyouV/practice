package com.hwp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PKCEUtil {

    public static void main(String[] args) {
        PKCEUtil pkceUtil = new PKCEUtil();
        System.out.println(pkceUtil.generateCodeChallenge("c1233a1wd35124bf12e1eh21"));
    }

    public String generateCodeChallenge(String codeVerifier) {
        try {
            byte[] bytes = codeVerifier.getBytes("US-ASCII");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes, 0, bytes.length);
            byte[] digest = messageDigest.digest();
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
}