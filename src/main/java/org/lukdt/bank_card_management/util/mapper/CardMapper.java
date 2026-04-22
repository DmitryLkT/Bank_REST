package org.lukdt.bank_card_management.util.mapper;

import org.lukdt.bank_card_management.dto.CardResponse;
import org.lukdt.bank_card_management.entity.Card;
import org.lukdt.bank_card_management.util.EncryptionService;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    private final EncryptionService encryptionService;

    public CardMapper(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public CardResponse toResponse(Card card) {
        String decryptedNum = encryptionService.decryption(card.getCardNumberEncrypted());
        String maskedNum = maskCardNumber(decryptedNum);

        return new CardResponse(
                maskedNum,
                card.getOwner().getName(),
                card.getOwner().getSurname(),
                card.getExpiryDate(),
                card.getStatus().name(),
                card.getBalance()
        );

    }

    private String maskCardNumber(String cardNumber) {
        String last4 = cardNumber.substring(cardNumber.length() - 4);

        return String.format("**** **** **** %s", last4);
    }
}
