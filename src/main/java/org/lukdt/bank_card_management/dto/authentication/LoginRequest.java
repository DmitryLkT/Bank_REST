package org.lukdt.bank_card_management.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на аутентификацию")
public class LoginRequest {
    @NotBlank
    @Schema(description = "Логин пользователя", example = "login123")
    private String login;

    @NotBlank
    @Schema(description = "Пароль пользователя", example = "password321")
    private String password;

    public LoginRequest() {}
    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
