package org.lukdt.bank_card_management.service.adminService.adminServiceInterface;

import org.lukdt.bank_card_management.dto.CardResponse;
import org.lukdt.bank_card_management.dto.UserResponse;

public interface AdminService {
    CardResponse createCard(Long userId);

    void unblockingCard(Long cardId);

    void removeCard(Long cardId);

    void blockingUser(Long userId);

    void unblockingUser(Long userId);
}
