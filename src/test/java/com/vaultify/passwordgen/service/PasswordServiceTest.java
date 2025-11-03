package com.vaultify.passwordgen.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordServiceTest {

    private final PasswordService service = new PasswordService();

    @Test
    @DisplayName("Deve gerar senha com o tamanho correto")
    void shouldGeneratePasswordWithCorrectLength() {
        // Arrange
        var length = 12;

        // Act
        var password = service.generatePassword(length, true, true, true, true);

        // Assert
        assertEquals(length, password.length());
    }

    @Test
    @DisplayName("Deve lançar exceção se o tamanho for zero")
    void shouldThrowExceptionWhenLengthIsZero() {
        // Arrange
        var length = 0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                service.generatePassword(length, true, true, true, true)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção se nenhum tipo de caractere for selecionado")
    void shouldThrowExceptionWhenNoCharacterTypeSelected() {
        // Arrange
        var length = 10;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                service.generatePassword(length, false, false, false, false)
        );
    }

    @Test
    @DisplayName("Deve conter apenas letras minúsculas quando apenas minúsculas forem selecionadas")
    void shouldOnlyContainLowercaseWhenOnlyLowercaseSelected() {
        // Arrange
        var length = 20;

        // Act
        var password = service.generatePassword(length, false, false, false, true);

        // Assert
        assertTrue(password.matches("^[a-z]+$"));
    }

    @Test
    @DisplayName("Deve conter apenas letras maiúsculas quando apenas maiúsculas forem selecionadas")
    void shouldOnlyContainUppercaseWhenOnlyUppercaseSelected() {
        // Arrange
        var length = 20;

        // Act
        var password = service.generatePassword(length, false, false, true, false);

        // Assert
        assertTrue(password.matches("^[A-Z]+$"));
    }

    @Test
    @DisplayName("Deve conter apenas números quando apenas números forem selecionados")
    void shouldOnlyContainDigitsWhenOnlyNumbersSelected() {
        // Arrange
        var length = 20;

        // Act
        var password = service.generatePassword(length, false, true, false, false);

        // Assert
        assertTrue(password.matches("^[0-9]+$"));
    }

    @Test
    @DisplayName("Deve conter apenas símbolos quando apenas símbolos forem selecionados")
    void shouldContainSymbolsWhenSymbolsSelected() {
        // Arrange
        var length = 20;

        // Act
        var password = service.generatePassword(length, true, false, false, false);

        // Assert
        assertTrue(password.matches("^[!@#$%^&*()\\-_=+\\[\\]{}|;:,.<>?/]+$"));
    }

    @Test
    @DisplayName("Deve conter todos os tipos de caracteres quando todos forem selecionados")
    void shouldContainMixedCharactersWhenAllSelected() {
        // Arrange
        var length = 100;

        // Act
        var password = service.generatePassword(length, true, true, true, true);

        // Assert
        assertTrue(password.matches(".*[a-z].*"), "Deveria conter letra minúscula");
        assertTrue(password.matches(".*[A-Z].*"), "Deveria conter letra maiúscula");
        assertTrue(password.matches(".*[0-9].*"), "Deveria conter número");
        assertTrue(password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:,.<>?/].*"), "Deveria conter símbolo");
    }
}