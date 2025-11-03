package com.vaultify.passwordgen.controller;


import com.vaultify.passwordgen.dto.PasswordRequest;
import com.vaultify.passwordgen.dto.PasswordResponse;
import com.vaultify.passwordgen.service.PasswordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/passwords")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping
    public PasswordResponse generatePassword(@RequestBody PasswordRequest request) {
        return new PasswordResponse(passwordService.generatePassword(request.length()));
    }
}
