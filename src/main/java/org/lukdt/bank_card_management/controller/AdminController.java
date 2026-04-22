package org.lukdt.bank_card_management.controller;

import org.lukdt.bank_card_management.dto.CardResponse;
import org.lukdt.bank_card_management.service.adminService.adminServiceInterface.AdminService;
import org.lukdt.bank_card_management.service.cardService.cardServiceInterface.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    private final CardService cardService;

    public AdminController(AdminService adminService, CardService cardService) {
        this.adminService = adminService;
        this.cardService = cardService;
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<CardResponse> createCard(@PathVariable Long userId) {
       CardResponse response = adminService.createCard(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/cards/{cardId}/block")
    public ResponseEntity<Void> blockCardByAdmin(@PathVariable Long cardId) {
        cardService.blockCardByAdmin(cardId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/cards/{cardId}/unblocking")
    public ResponseEntity<Void> unblockingCardByAdmin(@PathVariable Long cardId) {
        adminService.unblockingCard(cardId);

        return ResponseEntity.ok().build();
    }
}
