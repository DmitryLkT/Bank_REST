package org.lukdt.bank_card_management.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import org.lukdt.bank_card_management.entity.User;

@Schema(description = "Ответ после регистрации")
public class TokenResponse {
    @Schema(description = "Токен пользователя", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Тип токена", example = "Bearer")
    private String type;

    @Schema(description = "Id пользователя", example = "123")
    private Long userId;

    @Schema(description = "Роль пользователя", example = "USER")
    private String role;

    public TokenResponse() {}

    public TokenResponse(String token, User user) {
        this.token = token;
        this.type = "Bearer";
        this.userId = user.getId();
        this.role = user.getRole().name();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
