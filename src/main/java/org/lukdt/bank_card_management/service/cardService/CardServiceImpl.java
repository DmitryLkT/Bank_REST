package org.lukdt.bank_card_management.service.cardService;

import jakarta.persistence.EntityNotFoundException;
import org.lukdt.bank_card_management.dto.CardResponse;
import org.lukdt.bank_card_management.entity.Card;
import org.lukdt.bank_card_management.entity.Status;
import org.lukdt.bank_card_management.repository.CardRepository;
import org.lukdt.bank_card_management.repository.spec.CardSpecifications;
import org.lukdt.bank_card_management.service.cardService.cardServiceInterface.CardService;
import org.lukdt.bank_card_management.service.userService.userServiceInterface.UserService;
import org.lukdt.bank_card_management.util.mapper.CardMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService{
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UserService userService;

    @Override
    public Page<CardResponse> getUserCards(Long ownerId, String query, Pageable pageable) {
        userService.existsById(ownerId);

        Specification<Card> spec = buildSpecification(ownerId, query);

        return cardRepository.findAll(spec, pageable).map(cardMapper::toResponse);
    }

    private Specification<Card> buildSpecification(Long userId, String query) {
        Specification<Card> spec = CardSpecifications.ownerIdEquals(userId);

        if(query != null && !query.isBlank()) {
            Status status = parseStatus(query);
            if(status != null) {
                return spec.and(CardSpecifications.statusEquals(status));
            }
        }

        return spec;
    }

    private Status parseStatus(String text) {
        try {
            return Status.valueOf(text.toUpperCase());
        } catch(IllegalArgumentException e) {
            return null;
        }

    }

    public CardServiceImpl(CardRepository cardRepository, CardMapper cardMapper, UserService userService) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.userService = userService;
    }
}
