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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CardServiceImpl implements CardService{
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UserService userService;

    @Override
    public void blockUserCard(Long userId, Long cardId) {
        Card card = cardRepository.findByIdAndOwnerId(cardId, userId)
                .orElseThrow();//TODO прописать

        if(card.getStatus() == Status.BLOCKED) {throw new IllegalStateException("Card is already blocked");}
        if(card.getStatus() == Status.EXPIRED) {throw new IllegalStateException("Cannot block expired card");}

        card.setFlagBlock(true);
        cardRepository.save(card);
    }

    @Override
    public void blockCardByAdmin(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow();//TODO прописать

        if(!card.isFlagBlock()) {throw new IllegalStateException("The user did not request to be blocked");}

        card.setStatus(Status.BLOCKED);
        cardRepository.save(card);
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public int expireOutdatedCards() {
        LocalDate today = LocalDate.now();

        List<Card> expiredCards = cardRepository.findByStatusAndExpiryDateBefore(Status.ACTIVE, today);

        if(expiredCards.isEmpty()) return 0;

        expiredCards.forEach(card -> card.setStatus(Status.EXPIRED));
        cardRepository.saveAll(expiredCards);

        return expiredCards.size();
    }

    @Override
    public Page<CardResponse> getAllCards(Long ownerId, Pageable pageable) {
        Specification<Card> spec = Specification.where(null);

        if(ownerId != null) {
            spec = spec.and(CardSpecifications.ownerIdEquals(ownerId));
        }

        return cardRepository.findAll(spec, pageable)
                .map(cardMapper::toResponse);
    }

    @Override
    @Transactional
    public void moneyTransfer(Long userId, Long senderId, Long recipientId, BigDecimal summa){
        if(senderId.equals(recipientId)) { throw new IllegalStateException("Cannot transfer to the same card");}
        if(summa.compareTo(BigDecimal.ZERO) <= 0) { throw new IllegalStateException("Amount must be positive");}

        Card sender = cardRepository.findByIdAndOwnerId(senderId, userId)
                .orElseThrow();//TODO

        Card recipient = cardRepository.findByIdAndOwnerId(recipientId, userId)
                .orElseThrow();//TODO

        if(sender.getStatus() != Status.ACTIVE) {
            throw new IllegalStateException("Sender card is not active");
        }
        if(recipient.getStatus() != Status.ACTIVE) {
            throw new IllegalStateException("Recipient card is not active");
        }

        sender.setBalance(sender.getBalance().subtract(summa));
        recipient.setBalance(sender.getBalance().add(summa));

        cardRepository.save(sender);
        cardRepository.save(recipient);
    }

    @Override
    public BigDecimal getBalance(Long userId, Long cardId) {
        Card card = cardRepository.findByIdAndOwnerId(cardId, userId)
                .orElseThrow();//todo
        return card.getBalance();
    }

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
