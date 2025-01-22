package com.gmf.user_management.core.utils;

import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordUtil {

    public static String generatePassword(String input, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(input);
    }
}
