package org.lukdt.bank_card_management.exception.customException;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(Long id) {
        super(String.format("Card not found id={%d}", id));
    }
}
