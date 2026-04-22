package org.lukdt.bank_card_management.exception.customException;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(String.format("Login already exists: %s", message));
    }
}
