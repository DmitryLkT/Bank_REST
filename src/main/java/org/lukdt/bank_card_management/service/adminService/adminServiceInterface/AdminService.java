package org.lukdt.bank_card_management.service.adminService.adminServiceInterface;

import org.lukdt.bank_card_management.dto.CardResponse;

public interface AdminService {
    CardResponse createCard(Long userId);

    void unblockingCard(Long cardId);
}
