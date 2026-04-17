package org.lukdt.bank_card_management.service.userService.userServiceInterface;

import org.lukdt.bank_card_management.dto.CardResponse;

import java.math.BigDecimal;
import java.util.List;

public interface UserServiceInterface {
    List<CardResponse> getAllCards();

    boolean blockedCard(Long cardId);

    boolean transactionBetweenUserCards(Long sender, Long payee);

    BigDecimal getBalance(Long cardId);
}
