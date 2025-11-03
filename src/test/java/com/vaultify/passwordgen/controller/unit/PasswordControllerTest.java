package com.vaultify.passwordgen.controller.unit;

import com.vaultify.passwordgen.controller.PasswordController;
import com.vaultify.passwordgen.service.PasswordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PasswordController.class)
class PasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PasswordService passwordService;

    @Test
    @DisplayName("Deve retornar senha gerada com status 200")
    void shouldReturnGeneratedPassword() throws Exception {
        // Arrange
        var expectedPassword = "Abc123!";
        var requestJson = """
                {
                  "length": 7,
                  "includeSpecialChars": true,
                  "includeNumbers": true,
                  "includeUppercase": true,
                  "includeLowercase": true
                }
                """;

        when(passwordService.generatePassword(7, true, true, true, true))
                .thenReturn(expectedPassword);

        // Act & Assert
        mockMvc.perform(post("/api/passwords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value(expectedPassword));
    }

    @Test
    @DisplayName("Deve retornar 400 quando serviço lança IllegalArgumentException")
    void shouldReturnBadRequestWhenServiceThrowsIllegalArgumentException() throws Exception {
        // Arrange
        var requestJson = """
                {
                  "length": 0,
                  "includeSpecialChars": true,
                  "includeNumbers": true,
                  "includeUppercase": true,
                  "includeLowercase": true
                }
                """;

        when(passwordService.generatePassword(0, true, true, true, true))
                .thenThrow(new IllegalArgumentException("Tamanho inválido"));

        // Act & Assert
        mockMvc.perform(post("/api/passwords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Tamanho inválido"));
    }
}