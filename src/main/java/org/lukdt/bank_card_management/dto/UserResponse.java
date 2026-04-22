package org.lukdt.bank_card_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="Ответ с данными пользователя")
public class UserResponse {
    @Schema(description="id пользователя")
    private Long id;

    @Schema(description="Имя пользователя")
    private String name;

    @Schema(description="Фамилия пользователя")
    private String surname;

    @Schema(description="Возраст пользователя")
    private int age;

    @Schema(description="Логин пользователя")
    private String login;

    public UserResponse() {}
    public UserResponse(Long id, String name, String surname, int age, String login) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
