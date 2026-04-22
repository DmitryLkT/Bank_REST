package org.lukdt.bank_card_management.controller;

import jakarta.validation.Valid;
import org.lukdt.bank_card_management.dto.authentication.LoginRequest;
import org.lukdt.bank_card_management.dto.authentication.RegisterRequest;
import org.lukdt.bank_card_management.dto.authentication.TokenResponse;
import org.lukdt.bank_card_management.service.userService.userServiceInterface.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok("Registration was successful");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
