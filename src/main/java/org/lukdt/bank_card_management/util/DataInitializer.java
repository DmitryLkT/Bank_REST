package org.lukdt.bank_card_management.util;

import org.lukdt.bank_card_management.entity.Card;
import org.lukdt.bank_card_management.entity.Role;
import org.lukdt.bank_card_management.entity.Status;
import org.lukdt.bank_card_management.entity.User;
import org.lukdt.bank_card_management.repository.CardRepository;
import org.lukdt.bank_card_management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public DataInitializer(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("start checking DB");

        User user = null;
        Card card = null;

        try {
            log.info("access verification");
            userRepository.count();
            cardRepository.count();

            log.info("Inserting test data");
            user = new User();
            user.setName("name");
            user.setSurname("surname");
            user.setAge(25);
            user.setRole(Role.USER);
            user.setLogin("login");
            user.setPassword("password");
            user = userRepository.save(user);

            card = new Card();
            card.setCardNumberEncrypted("1234567891234567");
            card.setOwner(user);
            card.setExpiryDate(LocalDate.now());
            card.setStatus(Status.ACTIVE);
            card.setBalance(new BigDecimal(100));
            card = cardRepository.save(card);

            log.info("test data OK: userId={}, cardId={}", user.getId(), card.getId());

        } catch(Exception e) {
            log.error("ERROR: {}", e.getMessage());
        } finally {
            if(user != null) { userRepository.delete(user); }
            if(card != null) { cardRepository.delete(card); }

        }
    }
}
