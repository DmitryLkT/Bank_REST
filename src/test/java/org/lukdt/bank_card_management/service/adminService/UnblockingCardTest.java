package org.lukdt.bank_card_management.service.adminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lukdt.bank_card_management.entity.Card;
import org.lukdt.bank_card_management.entity.Status;
import org.lukdt.bank_card_management.entity.User;
import org.lukdt.bank_card_management.exception.customException.CardNotFoundException;
import org.lukdt.bank_card_management.repository.CardRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnblockingCardTest {
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private AdminServiceImpl adminService;

    Card card;

    @BeforeEach
    void setUp() {
        card = new Card();
        card.setId(1L);
        card.setCardNumberEncrypted("1234123412341234");
        card.setOwner(new User());
        card.setExpiryDate(LocalDate.now().plusYears(5));
        card.setStatus(Status.BLOCKED);
        card.setBalance(new BigDecimal(1000));
    }

    @Test
    void unblockingCard_CorrectData() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(card)).thenAnswer(inv -> {
                Card card = inv.getArgument(0);
                card.setStatus(Status.ACTIVE);
                return card;
        });

        adminService.unblockingCard(1L);

        assertEquals(Status.ACTIVE, card.getStatus());
    }

    @Test
    void unblockingCard_CardNotFound() {
        when(cardRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                () -> adminService.unblockingCard(10L));

        verify(cardRepository, never()).save(any());
    }

    @Test
    void unblockingCard_CardIsActive() {
        card.setStatus(Status.ACTIVE);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        assertThrows(IllegalStateException.class,
                () -> adminService.unblockingCard(1L));

        verify(cardRepository, never()).save(any());
    }

    @Test
    void unblockingCard_CardIsExpired() {
        card.setStatus(Status.EXPIRED);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        assertThrows(IllegalStateException.class,
                () -> adminService.unblockingCard(1L));

        verify(cardRepository, never()).save(any());
    }
}
