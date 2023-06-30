package com.gmf.user_management.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class PasswordUtil {
    public static String generatePassword(String input, String hash) {
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance(hash);

            // Add the input string bytes to the digest
            md.update(input.getBytes());

            // Get the hash bytes
            byte[] hashBytes = md.digest();

            // Convert the hash bytes to a hexadecimal representation
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            // Return the MD5 hash as a string
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
