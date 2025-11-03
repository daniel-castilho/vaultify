package com.vaultify.passwordgen.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordService {

    private static final SecureRandom random = new SecureRandom();

    public String generatePassword(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be greater than zero");
        }

        var sb = new StringBuilder(length);
        // Intervalo de caracteres imprimíveis ASCII (32 a 126)
        var lowerBound = 32;
        var upperBound = 126;

        for (int i = 0; i < length; i++) {
            int codePoint = lowerBound + random.nextInt(upperBound - lowerBound + 1);
            sb.append((char) codePoint);
        }

        return sb.toString();
    }
}
