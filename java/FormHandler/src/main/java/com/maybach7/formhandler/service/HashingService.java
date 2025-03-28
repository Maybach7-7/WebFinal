package com.maybach7.formhandler.service;

import com.maybach7.formhandler.exception.HashingException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class HashingService {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    private static final int HASH_ITERATIONS = 100000;

    private static final int HASH_KEY_LENGTH = 256;

    private HashingService() {}

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String toString(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }

    public static byte[] fromString(String string) {
        return Base64.getDecoder().decode(string);
    }

    public static byte[] getHash(String password, byte[] salt) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, HASH_ITERATIONS, HASH_KEY_LENGTH);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return hash;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exc) {
            throw new HashingException(exc);
        }
    }
}
