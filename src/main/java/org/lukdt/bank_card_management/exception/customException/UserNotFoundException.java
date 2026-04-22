package org.lukdt.bank_card_management.exception.customException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) {
        super(String.format("User not found login={%s}", login));
    }

    public UserNotFoundException(Long id) {
        super(String.format("User not found id={%d}", id));
    }
}
