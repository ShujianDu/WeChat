package com.yada;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Des {

    String encrypt(String data, String key) {
        try {
            SecretKey secretKey = get3DESKey(key);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptData = cipher.doFinal(data.getBytes("UTF-16LE"));
            return Base64.encodeBase64URLSafeString(encryptData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String decrypt(String data, String key) {
        try {
            SecretKey secretKey = get3DESKey(key);
            String decodeContent = URLDecoder.decode(data, "ISO8859-1");
            byte[] encryptData = Base64.decodeBase64(decodeContent);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptData = cipher.doFinal(encryptData);
            return new String(decryptData, "UTF-16LE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    SecretKey get3DESKey(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] digest = messageDigest.digest(key.getBytes("GBK"));
        byte[] keyBts = new byte[24];
        for (int i = 0; i < 24; i++) {
            keyBts[i] = i < digest.length ? digest[i] : 0;
        }
        DESedeKeySpec dks = new DESedeKeySpec(keyBts);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        return keyFactory.generateSecret(dks);
    }
}
