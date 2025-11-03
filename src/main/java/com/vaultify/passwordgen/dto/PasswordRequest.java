package com.vaultify.passwordgen.dto;

public record PasswordRequest(
        int length,
        boolean includeSpecialChars,
        boolean includeNumbers,
        boolean includeUppercase,
        boolean includeLowercase) {
}
