package com.vaultify.passwordgen.controller.integration;

import com.vaultify.passwordgen.dto.PasswordRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PasswordControllerTestIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("POST /api/passwords: Deve gerar uma senha com todos os tipos de caracteres")
    void generatePassword_ShouldReturnPassword_WhenAllTypesSelected() {
        // Arrange
        var request = new PasswordRequest(16, true, true, true, true);

        // Act
        String password = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/passwords")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("password", notNullValue())
                .body("password.length()", equalTo(16))
                .extract()
                .jsonPath()
                .getString("password");

        // Assert
        assertTrue(password.matches(".*[a-z].*"), "Deveria conter letra minúscula");
        assertTrue(password.matches(".*[A-Z].*"), "Deveria conter letra maiúscula");
        assertTrue(password.matches(".*[0-9].*"), "Deveria conter número");
        assertTrue(password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:,.<>?/].*"), "Deveria conter símbolo");
    }

    @Test
    @DisplayName("POST /api/passwords: Deve retornar 400 quando nenhum tipo de caractere for selecionado")
    void generatePassword_ShouldReturnBadRequest_WhenNoCharacterTypeSelected() {
        // Arrange
        var request = new PasswordRequest(12, false, false, false, false);

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/passwords")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("POST /api/passwords: Deve retornar 400 quando o tamanho for zero")
    void generatePassword_ShouldReturnBadRequest_WhenLengthIsZero() {
        // Arrange
        var request = new PasswordRequest(0, true, true, true, true);

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/passwords")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("POST /api/passwords: Deve retornar 400 quando o tamanho for menor que 4")
    void generatePassword_ShouldReturnBadRequest_WhenLengthIsLessThanFour() {
        // Arrange
        var request = new PasswordRequest(3, true, true, true, true);

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/passwords")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("POST /api/passwords: Deve retornar 400 quando o tamanho for maior que 128")
    void generatePassword_ShouldReturnBadRequest_WhenLengthIsGreaterThanOneTwelveEight() {
        // Arrange
        var request = new PasswordRequest(129, true, true, true, true);

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/passwords")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("POST /api/passwords: Deve conter símbolo quando includeSpecialChars=true")
    void generatePassword_ShouldContainSymbol_WhenSymbolsEnabled() {
        // Arrange
        var request = new PasswordRequest(20, true, false, false, false);

        // Act
        String password = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/passwords")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("password");

        // Assert
        assertTrue(password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:,.<>?/].*"), "Deveria conter símbolo");
    }
}
