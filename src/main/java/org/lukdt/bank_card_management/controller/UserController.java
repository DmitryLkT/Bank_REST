package org.lukdt.bank_card_management.controller;

import jakarta.validation.Valid;
import org.lukdt.bank_card_management.dto.CardResponse;
import org.lukdt.bank_card_management.dto.TransferRequest;
import org.lukdt.bank_card_management.entity.User;
import org.lukdt.bank_card_management.service.cardService.cardServiceInterface.CardService;
import org.lukdt.bank_card_management.service.userService.userServiceInterface.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class UserController {
    private final CardService cardService;
    private final UserService userService;

    public UserController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping("/cards")
    public ResponseEntity<Page<CardResponse>> getUserCards(
            @RequestParam(required = false) String query,
            @PageableDefault(size = 10) Pageable pageable,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long ownerId = getUserIfFromUserDetails(userDetails);

        Page<CardResponse> cards = cardService.getUserCards(ownerId, query, pageable);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/cards/{cardId}")
    public ResponseEntity<BigDecimal> getBalance(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cardId) {
        Long ownerId = getUserIfFromUserDetails(userDetails);

        BigDecimal balance = cardService.getBalance(ownerId, cardId);

        return ResponseEntity.ok(balance);
    }

    @PatchMapping("/{cardId}/block")
    public ResponseEntity<Void> blockUserCard(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cardId) {
        Long ownerId = getUserIfFromUserDetails(userDetails);

        cardService.blockUserCard(ownerId, cardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> moneyTransfer(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody TransferRequest request) {
        Long ownerId = getUserIfFromUserDetails(userDetails);

        cardService.moneyTransfer(
                ownerId,
                request.getSenderId(),
                request.getRecipientId(),
                request.getSumma()
        );

        return ResponseEntity.ok().build();
    }

    private Long getUserIfFromUserDetails(UserDetails userDetails) {
        if(userDetails instanceof User user) {
            return user.getId();
        }

        return userService.findByLogin(userDetails.getUsername()).getId();
    }

}
