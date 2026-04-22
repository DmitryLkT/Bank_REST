package org.lukdt.bank_card_management.service.userService.userServiceInterface;

import org.lukdt.bank_card_management.dto.UserResponse;
import org.lukdt.bank_card_management.dto.authentication.LoginRequest;
import org.lukdt.bank_card_management.dto.authentication.RegisterRequest;
import org.lukdt.bank_card_management.dto.authentication.TokenResponse;

public interface UserService {
    boolean existsById(Long userId);

    void register(RegisterRequest request);

    TokenResponse login(LoginRequest request);

    UserResponse findByLogin(String login);
}
