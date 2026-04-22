package org.lukdt.bank_card_management.util;

import org.lukdt.bank_card_management.entity.Role;
import org.lukdt.bank_card_management.entity.User;
import org.lukdt.bank_card_management.repository.CardRepository;
import org.lukdt.bank_card_management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_LOGIN}")
    private String adminLogin;
    @Value("${ADMIN_PASSWORD}")
    private String adminPass;

    public DataInitializer(CardRepository cardRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(String... args) {
        log.info("Starting DataInitializer...");

        try {
            log.info("Checking database connection...");
            userRepository.count();
            cardRepository.count();
            log.info("Database connection OK");

            createAdminIfNotExists();

        } catch (Exception e) {
            log.error("Error in DataInitializer: {}", e.getMessage(), e);
        }
    }

    private void createAdminIfNotExists() {
        log.info("Checking if admin exists...");

        if (!userRepository.existsByLogin(adminLogin)) {
            log.info("Admin not found. Creating admin user...");

            User admin = new User();
            admin.setLogin(adminLogin);
            admin.setPassword(passwordEncoder.encode(adminPass));
            admin.setName("Admin");
            admin.setSurname("System");
            admin.setAge(30);
            admin.setRole(Role.ADMIN);
            admin.setLocked(false);

            userRepository.save(admin);
            log.info("Admin user created successfully with login: {}", adminLogin);
        } else {
            log.info("Admin user already exists with login: {}", adminLogin);
        }
    }
}
