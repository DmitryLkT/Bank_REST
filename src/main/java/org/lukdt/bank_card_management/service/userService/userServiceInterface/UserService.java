package org.lukdt.bank_card_management.service.userService.userServiceInterface;

import org.lukdt.bank_card_management.dto.authentication.LoginRequest;
import org.lukdt.bank_card_management.dto.authentication.RegisterRequest;
import org.lukdt.bank_card_management.entity.User;

public interface UserService {
    boolean existsById(Long userId);

    void register(RegisterRequest request);

    String login(LoginRequest request);

//    Page<CardResponse> getUserCards(Long userId, String query, Pageable pageable);
//
//    boolean blockedCard(Long cardId);
//
//    boolean transactionBetweenUserCards(Long sender, Long payee);
//
//    BigDecimal getBalance(Long cardId);
}
