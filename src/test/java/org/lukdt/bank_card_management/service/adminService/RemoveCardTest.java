package org.lukdt.bank_card_management.service.adminService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lukdt.bank_card_management.entity.Card;
import org.lukdt.bank_card_management.exception.customException.CardNotFoundException;
import org.lukdt.bank_card_management.repository.CardRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RemoveCardTest {
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    void removeCard_CorrectData() {
        Card card = new Card();
        card.setId(1L);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        adminService.removeCard(1L);

        verify(cardRepository).delete(card);
    }

    @Test
    void removeCard_CardNotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                () -> adminService.removeCard(1L));

        verify(cardRepository, never()).delete(any(Card.class));
    }
}
