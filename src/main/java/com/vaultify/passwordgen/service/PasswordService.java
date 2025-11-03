package com.vaultify.passwordgen.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordService {

    private static final SecureRandom random = new SecureRandom();

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?/";

    public String generatePassword(final int length,
                                   final boolean includeSpecialChars,
                                   final boolean includeNumbers,
                                   final boolean includeUppercase,
                                   final boolean includeLowercase) {

        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be greater than zero");
        }

        var pool = new StringBuilder();

        if (includeLowercase) pool.append(LOWERCASE);
        if (includeUppercase) pool.append(UPPERCASE);
        if (includeNumbers) pool.append(NUMBERS);
        if (includeSpecialChars) pool.append(SYMBOLS);

        if (pool.length() == 0) {
            throw new IllegalArgumentException("At least one character type must be selected");
        }

        var password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            var index = random.nextInt(pool.length());
            password.append(pool.charAt(index));
        }

        return password.toString();
    }
}
