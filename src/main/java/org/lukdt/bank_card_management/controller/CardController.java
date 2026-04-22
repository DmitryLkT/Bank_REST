package org.lukdt.bank_card_management.controller;

import org.lukdt.bank_card_management.dto.CardResponse;
import org.lukdt.bank_card_management.service.cardService.cardServiceInterface.CardService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bank/cards")
public class CardController {
    private final CardService cardService;

    @GetMapping
    public Page<CardResponse> getUserCards() {return null;}
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }
}
