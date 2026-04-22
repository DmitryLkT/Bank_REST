package org.lukdt.bank_card_management.service.adminService;

import jakarta.persistence.EntityNotFoundException;
import org.lukdt.bank_card_management.dto.CardResponse;
import org.lukdt.bank_card_management.dto.UserResponse;
import org.lukdt.bank_card_management.entity.Card;
import org.lukdt.bank_card_management.entity.Status;
import org.lukdt.bank_card_management.entity.User;
import org.lukdt.bank_card_management.repository.CardRepository;
import org.lukdt.bank_card_management.repository.UserRepository;
import org.lukdt.bank_card_management.service.adminService.adminServiceInterface.AdminService;
import org.lukdt.bank_card_management.service.userService.userServiceInterface.UserService;
import org.lukdt.bank_card_management.util.EncryptionService;
import org.lukdt.bank_card_management.util.mapper.CardMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final EncryptionService encryptionService;
    private final CardMapper cardMapper;

    public AdminServiceImpl(UserRepository userRepository,CardRepository cardRepository, CardMapper cardMapper, EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.encryptionService = encryptionService;
    }

    @Override
    public CardResponse createCard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with  id: " + userId));

        String encrypt = encryptionService.encrypt(generationCardNumber());

        Card savedCard = cardRepository.save(new Card(
                encrypt,
                user,
                LocalDate.now(),
                Status.ACTIVE,
                new BigDecimal(1000)
        ));

        return cardMapper.toResponse(savedCard);
    }

    @Override
    public void unblockingCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow();//TODO прописать

        if(card.getStatus() == Status.ACTIVE) {throw new IllegalStateException("Card is already active");}
        if(card.getStatus() == Status.EXPIRED) {throw new IllegalStateException("Cannot block expired card");}

        card.setStatus(Status.ACTIVE);
        cardRepository.save(card);
    }

    @Override
    public void removeCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow();//TODO прописать
        cardRepository.delete(card);
    }

    @Override
    public void blockingUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow();//TODO прописать

        user.setLocked(true);
        userRepository.save(user);
    }

    @Override
    public void unblockingUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow();//TODO прописать

        user.setLocked(false);
        userRepository.save(user);
    }

    private String generationCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
