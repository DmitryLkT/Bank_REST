package org.lukdt.bank_card_management.service.cardService.cardServiceInterface;

import org.lukdt.bank_card_management.dto.CardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface CardService {
    Page<CardResponse> getUserCards(Long ownerId, String query, Pageable pageable);

    Page<CardResponse> getAllCards(Long ownerId, Pageable pageable);

    void blockUserCard(Long userId, Long cardId);

    void blockCardByAdmin(Long cardId);

    int expireOutdatedCards();

    void moneyTransfer(Long userId, Long senderId, Long recipientId, BigDecimal summa);

    BigDecimal getBalance(Long userId, Long cardId);
}
