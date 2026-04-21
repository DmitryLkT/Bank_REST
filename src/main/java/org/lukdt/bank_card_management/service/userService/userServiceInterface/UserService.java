package org.lukdt.bank_card_management.service.userService.userServiceInterface;

import org.lukdt.bank_card_management.dto.CardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface UserServiceInterface {
    boolean existsById();

//    Page<CardResponse> getUserCards(Long userId, String query, Pageable pageable);
//
//    boolean blockedCard(Long cardId);
//
//    boolean transactionBetweenUserCards(Long sender, Long payee);
//
//    BigDecimal getBalance(Long cardId);
}
