package org.lukdt.bank_card_management.repository;

import org.lukdt.bank_card_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    boolean existsLogin(String login);
}
