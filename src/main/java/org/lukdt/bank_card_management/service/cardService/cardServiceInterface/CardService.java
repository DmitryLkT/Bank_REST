package org.lukdt.bank_card_management.service.cardService.cardServiceInterface;

import org.lukdt.bank_card_management.dto.CardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {
    Page<CardResponse> getUserCards(Long ownerId, String query, Pageable pageable);

    Page<CardResponse> getAllCards(Long ownerId, Pageable pageable);

    void blockUserCard(Long userId, Long cardId);

    void blockCardByAdmin(Long cardId);

    int expireOutdatedCards();
}
