package org.lukdt.bank_card_management.controller;

import org.lukdt.bank_card_management.dto.CardResponse;
import org.lukdt.bank_card_management.entity.User;
import org.lukdt.bank_card_management.security.CustomUserDetailsService;
import org.lukdt.bank_card_management.service.cardService.cardServiceInterface.CardService;
import org.lukdt.bank_card_management.service.userService.userServiceInterface.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("bank/cards")
public class CardController {
    private final CardService cardService;
    private final UserService userService;

    public CardController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping("/account")
    public ResponseEntity<Page<CardResponse>> getUserCards(
            @RequestParam(required = false) String query,
            @PageableDefault(size = 10) Pageable pageable,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long ownerId = getUserIfFromUserDetails(userDetails);

        Page<CardResponse> cards = cardService.getUserCards(ownerId, query, pageable);
        return ResponseEntity.ok(cards);
    }

    private Long getUserIfFromUserDetails(UserDetails userDetails) {
        if(userDetails instanceof User user) {
            return user.getId();
        }

        return userService.findByLogin(userDetails.getUsername()).getId();
    }

}
