package com.vaultify.passwordgen.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public record PasswordRequest(
        @Min(value = 4, message = "O tamanho mínimo da senha deve ser 4")
        @Max(value = 128, message = "O tamanho máximo da senha deve ser 128")
        int length,
        boolean includeSpecialChars,
        boolean includeNumbers,
        boolean includeUppercase,
        boolean includeLowercase) {
}
