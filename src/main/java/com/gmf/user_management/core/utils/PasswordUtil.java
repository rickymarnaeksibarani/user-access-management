package com.gmf.user_management.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordUtil {

    @Autowired
    protected static PasswordEncoder passwordEncoder;

    public static String generatePassword(String input, String hash) {
        return passwordEncoder.encode(input);
    }
}
