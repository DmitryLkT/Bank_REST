package org.lukdt.bank_card_management.service.adminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lukdt.bank_card_management.dto.CardResponse;
import org.lukdt.bank_card_management.entity.Card;
import org.lukdt.bank_card_management.entity.Status;
import org.lukdt.bank_card_management.entity.User;
import org.lukdt.bank_card_management.exception.customException.UserNotFoundException;
import org.lukdt.bank_card_management.repository.CardRepository;
import org.lukdt.bank_card_management.repository.UserRepository;
import org.lukdt.bank_card_management.util.EncryptionService;
import org.lukdt.bank_card_management.util.mapper.CardMapper;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCardTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private EncryptionService encryptionService;
    @Mock
    private CardMapper cardMapper;
    @InjectMocks
    private AdminServiceImpl adminService;

    private User user;
    private CardResponse cardResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Dmitry");
        user.setSurname("Luk");

        cardResponse = new CardResponse();
        cardResponse.setCardNumber("**** **** **** 1234");
        cardResponse.setOwnerName("Dmitry");
        cardResponse.setOwnerSurname("Luk");
        cardResponse.setExpiryDate(LocalDate.now().plusYears(5));
        cardResponse.setStatus("ACTIVE");
        cardResponse.setBalance(new BigDecimal(1000));
    }

    @Test
    void createCard_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(encryptionService.encrypt(anyString())).thenReturn("encrypted_number");
        when(cardRepository.save(any(Card.class))).thenAnswer(inv -> {
            Card card = inv.getArgument(0);
            card.setId(100L);
            return card;
        });
        when(cardMapper.toResponse(any(Card.class))).thenReturn(cardResponse);

        CardResponse response = adminService.createCard(1L);

        assertNotNull(response);
        assertEquals("**** **** **** 1234", response.getCardNumber());
        assertEquals("Dmitry", response.getOwnerName());
        assertEquals("Luk", response.getOwnerSurname());
        assertEquals("ACTIVE", response.getStatus());
        assertEquals(new BigDecimal(1000), response.getBalance());
        verify(cardRepository).save(any(Card.class));
        verify(cardMapper).toResponse(any(Card.class));
    }

    @Test
    void createCardShouldThrowUserNotFoundExceptionWhenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> adminService.createCard(999L));

        verify(cardRepository, never()).save(any());
        verify(encryptionService, never()).encrypt(anyString());
    }

    @Test
    void createCardShouldSetDefaultValuesWhenCreated() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(encryptionService.encrypt(anyString())).thenReturn("encrypted_number");

        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
        when(cardRepository.save(cardCaptor.capture())).thenAnswer(inv -> {
            Card card = inv.getArgument(0);
            card.setId(100L);
            return card;
        });
        when(cardMapper.toResponse(any(Card.class))).thenReturn(cardResponse);

        adminService.createCard(1L);

        Card savedCard = cardCaptor.getValue();
        assertEquals(user, savedCard.getOwner());
        assertEquals(Status.ACTIVE, savedCard.getStatus());
        assertEquals(new BigDecimal(1000), savedCard.getBalance());
        assertEquals("encrypted_number", savedCard.getCardNumberEncrypted());
        assertNotNull(savedCard.getExpiryDate());
    }
}
