package org.lukdt.bank_card_management.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на регистрацию")
public class RegisterRequest {
    @NotBlank
    @Schema(description = "Имя пользователя", example = "Dmitry")
    private String name;

    @NotBlank
    @Schema(description = "Фамилия пользователя", example = "L")
    private String surname;

    @Min(18)
    @Schema(description = "Возраст пользователя", example = "35")
    private int age;

    @NotBlank
    @Schema(description = "Логин пользователя", example = "login123")
    private String login;

    @NotBlank
    @Schema(description = "Пароль пользователя", example = "password321")
    private String password;

    public RegisterRequest() {}

    public RegisterRequest(String name, String surname, int age, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
