package org.lukdt.bank_card_management.service.userService.userServiceInterface;

public interface UserService {
    boolean existsById(Long userId);

//    Page<CardResponse> getUserCards(Long userId, String query, Pageable pageable);
//
//    boolean blockedCard(Long cardId);
//
//    boolean transactionBetweenUserCards(Long sender, Long payee);
//
//    BigDecimal getBalance(Long cardId);
}
